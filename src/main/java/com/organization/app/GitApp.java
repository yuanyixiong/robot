package com.organization.app;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.util.NumberUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
import reactor.tuple.*;

import java.io.File;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class GitApp {

    public static void main(String[] args) throws Exception {
        String localRepoPath = "/Volumes/Office/hundsun";
        String remoteRepoURI = "git://192.168.8.6/hundsun.git";

        Git git = getGit(localRepoPath, remoteRepoURI);
        Map<String, Ref> branchList = Git.lsRemoteRepository().setRemote(remoteRepoURI).callAsMap();
        Ref branch = branchList.get("refs/heads/feature/a_20200326");
        Repository repository = git.checkout().setName(branch.getName()).getRepository();

//        // 指定分支最新提交
//        Tuple2<Tuple4<String, String, String, String>, List<Tuple5<String, String, String, String, String>>> tuple2 = getCommitLog(repository, branch);
//        System.out.println("提交时间:" + tuple2.getT1().getT1());
//        System.out.println("提交作者:" + tuple2.getT1().getT2());
//        System.out.println("作者邮箱:" + tuple2.getT1().getT3());
//        System.out.println("提交备注:" + tuple2.getT1().getT4());
//        System.out.println("文件列表:");
//        for (int i = 0; i < tuple2.getT2().size(); i++) {
//            System.out.println("---------------------------" + (i + 1) + ".[" + tuple2.getT2().get(i).getT2() + "]-->[" + tuple2.getT2().get(i).getT4() + "]---------------------------");
//            System.out.println("文件操作:" + tuple2.getT2().get(i).getT1());
//            System.out.println("更新前数据:" + tuple2.getT2().get(i).getT3());
//            System.out.println("更新后文件:" + tuple2.getT2().get(i).getT5());
//        }
//
//        // 指定分支所有提交
//        for (Tuple2<Tuple4<String, String, String, String>, List<Tuple5<String, String, String, String, String>>> t : getCommitsLog(repository, getCommits(repository, branch.getObjectId()))) {
//            System.out.println("提交时间:" + t.getT1().getT1());
//            System.out.println("提交作者:" + t.getT1().getT2());
//            System.out.println("作者邮箱:" + t.getT1().getT3());
//            System.out.println("提交备注:" + t.getT1().getT4());
//            System.out.println("文件列表:");
//            for (int i = 0; i < t.getT2().size(); i++) {
//                System.out.println("---------------------------" + (i + 1) + ".[" + t.getT2().get(i).getT2() + "]-->[" + t.getT2().get(i).getT4() + "]---------------------------");
//                System.out.println("文件操作:" + t.getT2().get(i).getT1());
//                System.out.println("更新前数据:" + t.getT2().get(i).getT3());
//                System.out.println("更新后文件:" + t.getT2().get(i).getT5());
//            }
//        }
//
        // 指定分支最近一天提交
        for (Tuple2<Tuple4<String, String, String, String>, List<Tuple5<String, String, String, String, String>>> t : getCommitsLog(repository, getCommits(repository, branch, 1))) {
            System.out.println("提交时间:" + t.getT1().getT1());
            System.out.println("提交作者:" + t.getT1().getT2());
            System.out.println("作者邮箱:" + t.getT1().getT3());
            System.out.println("提交备注:" + t.getT1().getT4());
            System.out.println("文件列表:");
            for (int i = 0; i < t.getT2().size(); i++) {
                System.out.println("---------------------------" + (i + 1) + ".[" + t.getT2().get(i).getT2() + "]-->[" + t.getT2().get(i).getT4() + "]---------------------------");
                System.out.println("文件操作:" + t.getT2().get(i).getT1());
                System.out.println("更新前数据:" + t.getT2().get(i).getT3());
                System.out.println("更新后文件:" + t.getT2().get(i).getT5());
            }
        }


//        for (Map.Entry<String, List<Tuple2<Tuple4<String, String, String, String>, List<Tuple5<String, String, String, String, String>>>>> entry : getCommitLog(localRepoPath, remoteRepoURI).entrySet()) {
//            System.out.println("===================================================[" + entry.getKey() + "]===================================================");
//            for (Tuple2<Tuple4<String, String, String, String>, List<Tuple5<String, String, String, String, String>>> t : entry.getValue()) {
//                System.out.println("提交时间:" + t.getT1().getT1());
//                System.out.println("提交作者:" + t.getT1().getT2());
//                System.out.println("作者邮箱:" + t.getT1().getT3());
//                System.out.println("提交备注:" + t.getT1().getT4());
//                System.out.println("文件列表:");
//                for (int i = 0; i < t.getT2().size(); i++) {
//                    System.out.println("---------------------------" + (i + 1) + ".[" + t.getT2().get(i).getT2() + "]-->[" + t.getT2().get(i).getT4() + "]---------------------------");
//                    System.out.println("文件操作:" + t.getT2().get(i).getT1());
//                    System.out.println("更新前数据:" + t.getT2().get(i).getT3());
//                    System.out.println("更新后文件:" + t.getT2().get(i).getT5());
//                }
//            }
//        }
    }


    /**
     * 获取git日志
     *
     * @param localRepoPath
     * @param remoteRepoURI
     * @return
     * @throws Exception
     */
    public static Map<String, List<Tuple2<Tuple4<String, String, String, String>, List<Tuple5<String, String, String, String, String>>>>> getCommitLog(String localRepoPath, String remoteRepoURI) throws Exception {
        Git git = getGit(localRepoPath, remoteRepoURI);
        Map<String, Ref> map = Git.lsRemoteRepository().setRemote(remoteRepoURI).callAsMap();
        Map<String, List<Tuple2<Tuple4<String, String, String, String>, List<Tuple5<String, String, String, String, String>>>>> result = Maps.newHashMap();

        for (String k : map.keySet()) {
            Ref v = map.get(k);
            if (v.getName().contains("gitblit/reflog")) {
                continue;
            }
            result.put(v.getName(), Lists.newArrayList());
            Repository repository = git.checkout().setName(v.getName()).getRepository();
            List<RevCommit> commitList = getCommits(repository, v, NumberUtils.INTEGER_ONE);
            for (int i = NumberUtils.INTEGER_ZERO; i < commitList.size(); i++) {
                RevCommit newCommit = commitList.get(i);
                RevCommit oldCommit = i == 0 ? null : commitList.get(i - 1);
                String commitDate = newCommit.getAuthorIdent().getWhen()
                        .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                result.get(v.getName())
                        .add(
                                Tuple.of(
                                        Tuple.of(
                                                commitDate,
                                                newCommit.getCommitterIdent().getName(),
                                                newCommit.getCommitterIdent().getEmailAddress(),
                                                newCommit.getFullMessage()
                                        ),
                                        getChangedFiles(newCommit, oldCommit, repository)
                                )
                        );
            }
        }
        return result;
    }

    public static Tuple2<Tuple4<String, String, String, String>, List<Tuple5<String, String, String, String, String>>> getCommitLog(Repository repository, Ref branch) throws Exception {
        /**单个分支最近一次提交**/
        RevCommit newCommit = getLatelyCommit(repository, branch.getObjectId());
        RevCommit oldCommit = getBeforeCommit(newCommit, repository);
        String commitDate = newCommit.getAuthorIdent().getWhen()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return Tuple.of(
                Tuple.of(
                        commitDate,
                        newCommit.getCommitterIdent().getName(),
                        newCommit.getCommitterIdent().getEmailAddress(),
                        newCommit.getFullMessage()
                ),
                getChangedFiles(newCommit, oldCommit, repository)
        );
    }

    public static List<Tuple2<Tuple4<String, String, String, String>, List<Tuple5<String, String, String, String, String>>>> getCommitsLog(Repository repository, List<RevCommit> commitList) throws Exception {
        List<Tuple2<Tuple4<String, String, String, String>, List<Tuple5<String, String, String, String, String>>>> result = Lists.newArrayList();
        for (RevCommit newCommit : commitList) {
            RevCommit oldCommit = getBeforeCommit(newCommit, repository);
            String commitDate = newCommit.getAuthorIdent().getWhen()
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            result.add(
                    Tuple.of(
                            Tuple.of(
                                    commitDate,
                                    newCommit.getCommitterIdent().getName(),
                                    newCommit.getCommitterIdent().getEmailAddress(),
                                    newCommit.getFullMessage()
                            ),
                            getChangedFiles(newCommit, oldCommit, repository)
                    )
            );
        }
        return result;
    }

    /**
     * 获取git
     *
     * @param localRepoPath
     * @param remoteRepoURI
     * @return
     * @throws IOException
     * @throws GitAPIException
     */
    public static Git getGit(String localRepoPath, String remoteRepoURI) throws IOException, GitAPIException {
        Git git;
        if (new File(localRepoPath).exists()) {
            git = Git.open(new File(localRepoPath));
        } else {
            git = Git.cloneRepository().setURI(remoteRepoURI).setDirectory(new File(localRepoPath)).call();
        }
        git.pull();
        return git;
    }


    /**
     * 获取最近n天的提交
     *
     * @param repository
     * @param latelyDays
     * @return
     * @throws Exception
     */
    public static List<RevCommit> getCommits(Repository repository, Ref ref, Integer latelyDays) throws Exception {
        RevCommit commit = getLatelyCommit(repository, ref.getObjectId());
        // 最近n天的提交
        Date day = Date.from(LocalDateTime.of(LocalDate.now(), LocalTime.MIN).plusDays(latelyDays * NumberUtils.INTEGER_MINUS_ONE).atZone(ZoneId.systemDefault()).toInstant());
        List<RevCommit> commitList = Lists.newArrayList();
        getCommits(commit, commitList, repository, day);
        return commitList;
    }

    /**
     * 获取的所有提交
     *
     * @param repository
     * @return
     * @throws Exception
     */
    public static List<RevCommit> getCommits(Repository repository, AnyObjectId commitId) throws Exception {
        RevCommit commit = getLatelyCommit(repository, commitId);
        return getCommits(commit, repository);
    }

    /***
     * 最近的一次提交
     * @param repository
     * @param commitId
     * @return
     * @throws IOException
     */
    public static RevCommit getLatelyCommit(Repository repository, AnyObjectId commitId) throws IOException {
        RevWalk rw = new RevWalk(repository);
        RevCommit commit = rw.parseCommit(commitId);
        return commit;
    }

    /**
     * 获取commit列表
     *
     * @param commit
     * @param repository
     * @throws Exception
     */
    public static void getCommits(RevCommit commit, List<RevCommit> commitList, Repository repository, Date commitDate) throws Exception {
        if (Objects.isNull(commit)) {
            return;
        }
        RevWalk walk = new RevWalk(repository);
        walk.markStart(commit);
        for (RevCommit rev : walk) {
            if (rev.getAuthorIdent().getWhen().before(commitDate)) {
                continue;
            }
            commitList.add(rev);
        }
        walk.dispose();
    }

    /**
     * 获取commit列表
     *
     * @param commit
     * @param repository
     * @throws Exception
     */
    public static List<RevCommit> getCommits(RevCommit commit, Repository repository) throws Exception {
        List<RevCommit> commitList = Lists.newArrayList();
        RevWalk walk = new RevWalk(repository);
        walk.markStart(commit);
        for (RevCommit rev : walk) {
            commitList.add(rev);
        }
        walk.dispose();
        return commitList;
    }

    /***
     * 获取当前提交的上一次提交
     * @param commit
     * @param repository
     * @return
     * @throws Exception
     */
    public static RevCommit getBeforeCommit(RevCommit commit, Repository repository) throws Exception {
        RevWalk walk = new RevWalk(repository);
        walk.markStart(commit);
        RevCommit rev = null;
        if (walk.iterator().hasNext()) {
            rev = walk.next();
        }
        walk.dispose();
        return rev;
    }

    /***
     * 获取commit的文件列表
     * @param newCommit
     * @param oldCommit
     * @param repository
     * @throws Exception
     */
    static List<Tuple5<String, String, String, String, String>> getChangedFiles(RevCommit newCommit, RevCommit oldCommit, Repository repository) throws IOException, GitAPIException {

        List<Tuple5<String, String, String, String, String>> result = Lists.newArrayList();

        ObjectReader newReader = repository.newObjectReader();
        CanonicalTreeParser newTree = new CanonicalTreeParser();
        newTree.reset(newReader, newCommit.getTree().getId());

        if (Objects.isNull(oldCommit)) {

            for (DiffEntry entry : new Git(repository).diff().setNewTree(newTree).call()) {
                TreeWalk newTreeWalk = TreeWalk.forPath(repository, entry.getNewPath(), newCommit.getTree());
                if (null == newTreeWalk) {
                    continue;
                }
                result.add(
                        Tuple.of(
                                entry.toString().replace("DiffEntry[", "").replace("]", ""), "", "",
                                newTreeWalk.getPathString(),
                                new String(repository.open(newTreeWalk.getObjectId(NumberUtils.INTEGER_ZERO)).getBytes())
                        )
                );
            }

        } else {
            ObjectReader olgReader = repository.newObjectReader();
            CanonicalTreeParser oldTree = new CanonicalTreeParser();
            oldTree.reset(olgReader, oldCommit.getTree().getId());

            for (DiffEntry entry : new Git(repository).diff().setNewTree(newTree).setOldTree(oldTree).call()) {

                String newFile = "", newFileData = "", oldFile = "", oldFileData = "";

                TreeWalk newTreeWalk = TreeWalk.forPath(repository, entry.getNewPath(), newCommit.getTree());
                if (null != newTreeWalk) {
                    newFile = newTreeWalk.getPathString();
                    newFileData = new String(repository.open(newTreeWalk.getObjectId(NumberUtils.INTEGER_ZERO)).getBytes());
                }

                TreeWalk oldTreeWalk = TreeWalk.forPath(repository, entry.getOldPath(), oldCommit.getTree());
                if (null != oldTreeWalk) {
                    oldFile = oldTreeWalk.getPathString();
                    oldFileData = new String(repository.open(oldTreeWalk.getObjectId(NumberUtils.INTEGER_ZERO)).getBytes());
                }

                result.add(Tuple.of(entry.toString().replace("DiffEntry[", "").replace("]", ""), oldFile, oldFileData, newFile, newFileData));
            }
        }
        return result;
    }
}
