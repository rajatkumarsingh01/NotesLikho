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
            echo "Detecting available AVDs..."
            AVAILABLE_AVD=$(emulator -list-avds | head -n 1)

            if [ -z "$AVAILABLE_AVD" ]; then
                echo "❌ No AVD found! Creating one..."
                echo no | avdmanager create avd -n Pixel_4a -k "system-images;android-30;google_apis;x86_64" -d pixel_4a
                AVAILABLE_AVD="Pixel_4a"
            fi

            echo "✅ Starting emulator: $AVAILABLE_AVD"
            nohup emulator -avd "$AVAILABLE_AVD" -no-audio -no-window &
            
            echo "⏳ Waiting for device to be ready..."
            adb wait-for-device
            adb shell input keyevent 82
            echo "⌛ Giving emulator time to fully boot..."
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
