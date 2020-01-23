#!/usr/bin/env groovy

def call(){
 
withSonarQubeEnv('sonar1') {
    //dir("${BACKEND_DIR}") {
 // sh 'echo "hello"'
   sh 'node sonar-project.js'
                   }
//} 
}
