#!/usr/bin/env groovy


def call() {
 sh 'npm install sonarqube-scanner -X switch --save-dev' 
}