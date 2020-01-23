#!/usr/bin/env groovy

def call(message){
    def body = """Check console output at ${BUILD_URL} to view the results.\nJob Summary\n${message}"""
    def subject = "Build failed in Jenkins: #${JOB_NAME} - #$BUILD_NUMBER"
    emailext body: body, subject: subject, to: 'darpan.patel@volansys.com'
}





/*def call() {
    
    emailext body: 'Check console output at $BUILD_URL to view the results. \\n\\n ${CHANGES} \\n\\n -------------------------------------------------- \\n${BUILD_LOG, maxLines=100, escapeHtml=false}', subject: 'Build failed in Jenkins: $PROJECT_NAME - #$BUILD_NUMBER', to: 'mayank.vekariya@volansys.com'


  }*/
  