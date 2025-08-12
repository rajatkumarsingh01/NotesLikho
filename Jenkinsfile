pipeline {
    agent any
    
     environment {
        ANDROID_HOME = "/home/idrbt/Android/Sdk"
        PATH = "${env.ANDROID_HOME}/cmdline-tools/latest/bin:${env.ANDROID_HOME}/platform-tools:${env.PATH}"
    }
    stages {

        
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/rajatkumarsingh01/NotesLikho.git'
            }
        }


  
        stage('Setup Android SDK Tools') {
            steps {
                sh '''
                    apt-get update && apt-get install -y unzip wget || true

                    if [ ! -d "${ANDROID_HOME}/cmdline-tools/latest" ]; then
                        echo "Installing Android command line tools..."
                        wget -q https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip -O commandlinetools.zip
                        mkdir -p ${ANDROID_HOME}/cmdline-tools
                        unzip -q commandlinetools.zip -d ${ANDROID_HOME}/cmdline-tools
                        mv ${ANDROID_HOME}/cmdline-tools/cmdline-tools ${ANDROID_HOME}/cmdline-tools/latest
                        rm commandlinetools.zip
                    else
                        echo "Command line tools already installed."
                    fi

                    yes | sdkmanager --sdk_root=${ANDROID_HOME} --licenses || true
                    sdkmanager --sdk_root=${ANDROID_HOME} "platform-tools" "platforms;android-33" "system-images;android-30;google_apis;x86_64"
                '''
            }
        }

       
        
        stage('Start Emulator') {
            steps {
                sh '''
                    export ANDROID_SDK_ROOT=${ANDROID_HOME}
                    export PATH=${ANDROID_HOME}/emulator:${ANDROID_HOME}/platform-tools:$PATH

                    AVAILABLE_AVD=$(emulator -list-avds | head -n 1)
                    if [ -z "$AVAILABLE_AVD" ]; then
                        echo "Creating AVD named 'MyAVD'..."
                        avdmanager create avd -n MyAVD -k "system-images;android-30;google_apis;x86_64" --device "pixel_4a" --force
                        AVAILABLE_AVD="MyAVD"
                    fi

                    echo "Starting emulator: $AVAILABLE_AVD"
                    nohup emulator -avd "$AVAILABLE_AVD" -no-audio -no-window &

                    adb wait-for-device
                    adb shell input keyevent 82
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
