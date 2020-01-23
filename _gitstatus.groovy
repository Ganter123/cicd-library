#!/usr/bin/env groovy

def call() {
   
    gitlabCommitStatus(builds: [[connection: gitLabConnection('volansys'), name: 'CICD-Volansys-Tool', state: 'failed', projectId: '852', revisionHash: 'devops']], connection: gitLabConnection('volansys'), name: '$BUILD_DISPLAY_NAME') {
    //updateGitlabCommitStatus name: '$JOB_NAME', state: 'success'
    //updateGitlabCommitStatus name: '$JOB_NAME', state: 'failed'
    // some block
}
}