#!/usr/bin/env groovy

def call(){
    
    sh './node_modules/.bin/nyc --reporter=cobertura node_modules/.bin/_mocha "test/**/*.js"'
//mocha -- Modules/**/Tests/*.js


//sh './node_modules/.bin/nyc --reporter=cobertura node_modules/.bin/_mocha "backend/api/**/*.js"'
}