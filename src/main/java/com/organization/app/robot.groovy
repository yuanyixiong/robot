package com.organization.app

import com.gitblit.GitBlit
import com.gitblit.Keys
import com.gitblit.models.RepositoryModel
import com.gitblit.models.TeamModel
import com.gitblit.models.UserModel
import com.gitblit.utils.JGitUtils
import java.text.SimpleDateFormat
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import java.net.URLEncoder;
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.lib.Config
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.transport.ReceiveCommand
import org.eclipse.jgit.transport.ReceiveCommand.Result
import org.slf4j.Logger

logger.info("sendmail hook triggered by ${user.username} for ${repository.name}")


Repository r = gitblit.getRepository(repository.name)

Config config = r.getConfig()
def mailinglist = config.getString('hooks', null, 'mailinglist')

def toAddresses = []

if (mailinglist != null) {
    def addrs = mailinglist.split(/(,|\s)/)
    toAddresses.addAll(addrs)
}

toAddresses.addAll(gitblit.getStrings(Keys.mail.mailingLists))

def teams = gitblit.getRepositoryTeams(repository)
for (team in teams) {
    TeamModel model = gitblit.getTeamModel(team)
    if (model.mailingLists) {
        toAddresses.addAll(model.mailingLists)
    }
}

toAddresses.addAll(repository.mailingLists)

def repo = repository.name
def summaryUrl
if (gitblit.getBoolean(Keys.web.mountParameters, true)) {
    repo = repo.replace('/', gitblit.getString(Keys.web.forwardSlashCharacter, '/')).replace('/', '%2F')
    summaryUrl = url + "/summary/$repo"
} else {
    summaryUrl = url + "/summary?r=$repo"
}

def commitCount = 0
def changes = ''
SimpleDateFormat df = new SimpleDateFormat(gitblit.getString(Keys.web.datetimestampLongFormat, 'yyyy-MM-dd hh:mm:ss'))
def table = { "认证信息：${JGitUtils.getDisplayName(it.authorIdent)}\n时间：${df.format(JGitUtils.getCommitDate(it))}\n描述：$it.shortMessage\n" }
for (command in commands) {
    def ref = command.refName
    def refType = 'branch'
    if (ref.startsWith('refs/heads/')) {
        ref  = command.refName.substring('refs/heads/'.length())
    } else if (ref.startsWith('refs/tags/')) {
        ref  = command.refName.substring('refs/tags/'.length())
        refType = 'tag'
    }

    switch (command.type) {
        case ReceiveCommand.Type.CREATE:
            def commits = JGitUtils.getRevLog(r, command.oldId.name, command.newId.name).reverse()
            commitCount += commits.size()
            changes += "new $refType $ref created ($commits.size commits)\n"
            changes += commits.collect(table)
            changes += '\n'
            break
        case ReceiveCommand.Type.UPDATE:
            def commits = JGitUtils.getRevLog(r, command.oldId.name, command.newId.name).reverse()
            commitCount += commits.size()
            changes += "$ref $refType updated ($commits.size commits)\n"
            changes += commits.collect(table)
            changes += '\n'
            break
        case ReceiveCommand.Type.UPDATE_NONFASTFORWARD:
            def commits = JGitUtils.getRevLog(r, command.oldId.name, command.newId.name).reverse()
            commitCount += commits.size()
            changes += "$ref $refType updated [NON fast-forward] ($commits.size commits)\n"
            changes += commits.collect(table)
            changes += '\n'
            break
        case ReceiveCommand.Type.DELETE:
            changes += "$ref $refType deleted\n"
            break
        default:
            break
    }
}
r.close()


def timestamp = System.currentTimeMillis();
def secret = "SEC8ea1b9174903d0047634543b8f604421e10b68343d4991dd321aebefd675e559";
def stringToSign = timestamp + "\n" + secret;
def mac = Mac.getInstance("HmacSHA256");
mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
def signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
def sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
def connection = new URL("https://oapi.dingtalk.com/robot/send?access_token=c4af8f3307c51e593fc235db99ea24fe62f560bd7afa1b11d9d1346babbc7320&timestamp="+timestamp+"&sign="+sign).openConnection()
connection.setRequestMethod('POST')
connection.doOutput = true
connection.setRequestProperty("Content-Type", "application/json")
def writer = new OutputStreamWriter(connection.outputStream,"utf-8")
def content ="{\"msgtype\": \"text\",\"text\": {\"content\": \""+"提交者：$user.username\n提交数量：$commitCount\n版本库：$repository.name"+"$summaryUrl\n版本：$it.id.name\n$changes"+" \"},\"at\": {\"atMobiles\": [],\"isAtAll\": false}}"
writer.write(content.toString())
writer.flush()
writer.close()
connection.connect()
def respText = connection.content.text
println respText