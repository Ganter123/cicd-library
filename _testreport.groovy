#!/usr/bin/env groovy

def call(){
 
 cobertura autoUpdateHealth: false, autoUpdateStability: false, coberturaReportFile: 'reports/cobertura-coverage.xml', conditionalCoverageTargets: '70, 0, 0', failUnhealthy: false, failUnstable: false, lineCoverageTargets: '80, 0, 0', maxNumberOfBuilds: 0, methodCoverageTargets: '80, 0, 0', onlyStable: false, zoomCoverageChart: false

}