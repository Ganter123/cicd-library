#!/usr/bin/env groovy

def call(Map pipelineParams) {
 pipeline {
    agent any
    tools {nodejs "node"}
environment {
     //   FRONTEND_DIR = 'front-end/portal'
             GITLAB_CONNECTION = 'volansys'
          GIT_USER = sh (script: 'git show -s --pretty=%an', returnStdout: true).trim()
      //  sonarqubeScannerHome = tool name: 'SonarQube'
    }
    stages {       
        stage('PreBuild') {
            steps {
                dir(pipelineParams.build_directory) {
	            sh pipelineParams.buildcmd
                
               
                }
            }
        } 
        stage ('Build') {
            steps {
            
                  dir(pipelineParams.build_directory) {
                  sh pipelineParams.angularbuild
                 // _angularbuild()
                    }
                }
                
           }
        
      stage ('Pretest') {
            steps {
               dir(pipelineParams.build_directory) {
               sh pipelineParams.angulartest
               // _angulartest()
                }
            }
        } 
        stage ('Publish Reports') {
            steps {
            dir(pipelineParams.build_directory) {
                  _tslintreport()
              //  _testreport()
               
             }
        }
        
        } 
        stage ('SonarScanner') {
             steps {
           dir(pipelineParams.build_directory) {
                 _sonar()
    //    withSonarQubeEnv('SonarQube') {
  //sh "${sonarqubeScannerHome}/bin/sonar-scanner -Dsonar.host.url=http://192.168.1.166:9050/ -Dproject.settings='/var/jenkins_home/workspace/votnode_devops-node/sonar-project.properties' -Dsonar.login=be4482dc542ada75a027903561c68107dd2941a9 -Dsonar.projectBaseDir=."
                      //  }
                      }
                    }
            }
        
         stage ('Release') {
             steps {
                  _angularfileoperations()
                 _angularartifacts()

             }
         }
    }
       
       
       post {
          failure {
              script{
                        def messages = _generatestagenotificationmessages(_pipelinestagedetail())
                        updateGitlabCommitStatus name: '"${JOB_NAME}"', state: 'failed'
                        _email("${messages['emailTemplate']}")
                      } 
          }
      
       always {
   
           addShortText(text: "${GIT_USER}", background: 'orange', border: 1) ;
            addShortText(text: "${GIT_BRANCH}", background: 'yellow', border: 1);
          addShortText(text: "${NODE_NAME}", background: 'cyan', border: 1) 
           
        //    cleanWs() /* clean up our workspace */
           }
  
      }
}    
     
}

