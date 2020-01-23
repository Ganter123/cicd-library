#!/usr/bin/env groovy

def call(){
//  sh './node_modules/.bin/eslint --max-warnings 2 --ext .js --f checkstyle . --f > eslint.xml'  
    sh './node_modules/.bin/eslint --max-warnings 2 --ext .js --f checkstyle . --fix -o eslint.xml'
   // sh './node_modules/.bin/eslint --max-warnings 2 --ext .js --f checkstyle . > eslint.xml'
}