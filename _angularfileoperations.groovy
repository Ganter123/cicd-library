#!/usr/bin/env groovy

def call(){

fileOperations([fileZipOperation('/var/jenkins_home/workspace/votnode_checkeslint/front-end/portal/dist')])
}
