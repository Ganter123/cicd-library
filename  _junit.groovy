#!/usr/bin/env groovy

def call(){
 
junit allowEmptyResults: true, testResults: 'front-end/portal/eslint.xml'
    
}
