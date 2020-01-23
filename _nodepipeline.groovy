#!/usr/bin/env groovy

def call(Map pipelineParams) {

  pipeline {
    agent any
    tools {nodejs "node"}
    environment {
     GITLAB_CONNECTION = 'volansys'
       GIT_USER = sh (script: 'git show -s --pretty=%an', returnStdout: true).trim()
       
       }
    
    stages {
      stage('PreBuild') {
            steps {
               dir(pipelineParams.build_directory) {
               sh pipelineParams.buildcmd
              }
            }
          }
         
         /* stage('build'){
          steps{
                    }
       } */
        
      stage ('Verify') {
            steps {
               dir(pipelineParams.build_directory) {
                 sh pipelineParams.eslintexecution
                sh pipelineParams.testexecution
                  }
                }
              }
       
      stage ('Publish Reports'){
          steps {
            dir(pipelineParams.build_directory) {
              _eslintreport()
              _testreport()
            }
          }
        }
      
      stage ('SonarScanner'){
        steps{
         dir(pipelineParams.build_directory) {
                _sonar()
                
             }
           }
         }
          stage ('Release') {
             steps {
                 _nodefileoperations()
                 _nodeartifacts()
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
            _sidebarlink()
            cleanWs() /* clean up our workspace */
           }
       }
 
      
      options {
      gitLabConnection('volansys')
    }
    
  } 
  }
  
