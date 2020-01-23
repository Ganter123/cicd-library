#!/usr/bin/env groovy

def call() {
 

// recordIssues enabledForFailure: true, aggregatingResults: true, tool: checkStyle(pattern: 'tslint.xml')

recordIssues enabledForFailure: true, aggregatingResults: true, tool: checkStyle(pattern:'eslint.xml')

// recordIssues enableForFailure: true tool: tsLint(pattern: 'eslint.xml'),
                 

}