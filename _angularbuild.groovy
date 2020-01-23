#!/usr/bin/env groovy

def call(){
    sh 'npm run ng build --prod --aot --buildOptimizer --commonChunk --vendorChunk --optimization --progress'
}