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
            export ANDROID_SDK_ROOT=/home/idrbt/Android/Sdk
            export PATH=$ANDROID_SDK_ROOT/emulator:$ANDROID_SDK_ROOT/platform-tools:$PATH

            AVAILABLE_AVD=$(emulator -list-avds | head -n 1)
            if [ -z "$AVAILABLE_AVD" ]; then
                echo "❌ No AVD found. Please create one first."
                exit 1
            fi

            echo "✅ Starting emulator: $AVAILABLE_AVD"
            nohup emulator -avd "$AVAILABLE_AVD" -no-audio -no-window &
            echo "⏳ Waiting for device to be ready..."
            adb wait-for-device
            adb shell input keyevent 82
            echo "⌛ Waiting for emulator to fully boot..."
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
