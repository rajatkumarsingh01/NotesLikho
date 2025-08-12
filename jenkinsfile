pipeline {
    agent any
    tools {
        jdk 'JDK17'
        gradle 'Gradle7'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/rajatkumarsingh01/NotesLikho.git'
            }
        }
        stage('Unit Tests') {
            steps {
                sh './gradlew test'
            }
        }
        stage('Instrumented Tests') {
            steps {
                sh './gradlew connectedAndroidTest'
            }
        }
    }
    post {
        always {
            junit '**/build/test-results/**/*.xml'
            junit '**/build/outputs/androidTest-results/**/*.xml'
        }
    }
}
