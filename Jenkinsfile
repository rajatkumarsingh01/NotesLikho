pipeline {
    agent any
    
     environment {
        ANDROID_HOME = '/home/idrbt/Android/Sdk'
        PATH = "$ANDROID_HOME/emulator:$ANDROID_HOME/platform-tools:$PATH"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/rajatkumarsingh01/NotesLikho.git'
            }
        }

stage('Debug Jenkins Env') {
    steps {
        sh '''
            whoami
            echo "HOME: $HOME"
            ls -R $HOME/.android || echo "No .android folder"
            emulator -list-avds || echo "No AVDs found"
        '''
    }
}


        stage('Start Emulator') {
            steps {
                sh '''
                    echo "Starting emulator: Pixel_4a"
                    nohup emulator -avd Pixel_4a -no-audio -no-window &
                    adb wait-for-device
                    adb shell input keyevent 82
                    echo "Waiting for emulator to fully boot..."
                    sleep 30
                '''
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
