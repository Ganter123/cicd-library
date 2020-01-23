#!/usr/bin/env groovy

def call() {
 

 recordIssues enabledForFailure: true, aggregatingResults: true, tool: checkStyle(pattern: 'tslint.xml')
 
 }