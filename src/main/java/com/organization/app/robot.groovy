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
def table = { "\n提交者：${JGitUtils.getDisplayName(it.authorIdent)}\n提交时间：${df.format(JGitUtils.getCommitDate(it))}\n提交描述：$it.shortMessage\n提交版本：$it.id.name\n" }
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
            changes += "资源：$ref \n类型：$refType \n操作：created ($commits.size commits)\n"
            changes += commits.collect(table)
            break
        case ReceiveCommand.Type.UPDATE:
            def commits = JGitUtils.getRevLog(r, command.oldId.name, command.newId.name).reverse()
            commitCount += commits.size()
            changes += "资源：$ref \n类型：$refType \n操作：updated ($commits.size commits)\n"
            changes += commits.collect(table)
            break
        case ReceiveCommand.Type.UPDATE_NONFASTFORWARD:
            def commits = JGitUtils.getRevLog(r, command.oldId.name, command.newId.name).reverse()
            commitCount += commits.size()
            changes += "资源：$ref \n类型：$refType \n操作：updated [NON fast-forward] ($commits.size commits)\n"
            changes += commits.collect(table)
            break
        case ReceiveCommand.Type.DELETE:
            changes += "资源：$ref \n类型：$refType \n操作：deleted"
            break
        default:
            break
    }
}
r.close()

def timestamp = System.currentTimeMillis();
def secret = "SECf479ce148f10199133698c39e9f7776c337742c0efbe53dda7b4396836b547ab";
def stringToSign = timestamp + "\n" + secret;
def mac = Mac.getInstance("HmacSHA256");
mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
def signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
def sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
def connection = new URL("https://oapi.dingtalk.com/robot/send?access_token=562675fc20be1ad0d46c8ecd1199ea7db3a368106c6d3579da87568c50bc6ff0&timestamp="+timestamp+"&sign="+sign).openConnection()
connection.setRequestMethod('POST')
connection.doOutput = true
connection.setRequestProperty("Content-Type", "application/json")
def writer = new OutputStreamWriter(connection.outputStream,"utf-8")
def content ="{\"msgtype\": \"text\",\"text\": {\"content\": \""+"推送者：$user.username\n推送数量：$commitCount\n版本库：$repository.name"+"$summaryUrl\n$changes"+" \"},\"at\": {\"atMobiles\": [],\"isAtAll\": true}}"
writer.write(content.toString())
writer.flush()
writer.close()
connection.connect()
def respText = connection.content.text
println respText