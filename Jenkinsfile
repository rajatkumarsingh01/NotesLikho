pipeline {
    agent any
   
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
stage('Start Emulator') {
    steps {
        sh '''
            nohup emulator -avd testAVD -no-audio -no-window &
            adb wait-for-device
            adb shell input keyevent 82
        '''
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
