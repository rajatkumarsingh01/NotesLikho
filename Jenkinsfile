pipeline {
    agent any

    
    environment {
    ANDROID_HOME = "/var/lib/jenkins/Android/Sdk"
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
            echo "Starting emulator: MyAVD"
            nohup emulator -avd MyAVD -no-audio -no-window &
            adb wait-for-device

            echo "Waiting for emulator to boot..."
            boot_completed=""
            until [ "$boot_completed" = "1" ]; do
                sleep 5
                boot_completed=$(adb shell getprop sys.boot_completed 2>/dev/null | tr -d '\r')
                echo "Boot status: $boot_completed"
            done

            echo "Emulator booted successfully, unlocking screen..."
            adb shell input keyevent 82
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
