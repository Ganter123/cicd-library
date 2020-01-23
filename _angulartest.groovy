#!/usr/bin/env groovy

def call(){
//    sh 'npm run ng test'
  //  sh 'npm run ng lint'

    
    sh 'npm run lint'
 //   sh 'npm run ng test'
//ng lint --format=checkstyle > tslint.xml

  //  sh 'npm run-script --silent lint > eslint.xml'
 
 // sh 'npm run-script --silent -- ng lint --format json --fix > eslint.json'
 
 
  //sh 'sed -i "$ d" eslint.json'
  
 
    
}




