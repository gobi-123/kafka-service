pipeline {
  agent any
  stages {
    stage('Git Clone') {
      steps {
        echo 'Clone the repo'
      }
    }

    stage('Build') {
      steps {
        sh '''echo PATH = ${PATH}
mvn -N io.takari:maven:wrapper
./mvnw clean install'''
      }
    }

  }
}