pipeline {
    agent { 
        docker {
            image "uptech/android-ci"
            label "docker"
            args "-u root"
        }
    }

//    environment {
//        POMODORO_KEYSTORE_ALIAS = credentials("POMODORO_KEYSTORE_ALIAS")
//        POMODORO_KEYSTORE_PASSWORD = credentials("POMODORO_KEYSTORE_PASSWORD")
//    }


    stages {
        stage("Checkout") { steps { checkout scm } }

        stage("Test") {
            steps {
                echo 'Hello World'
                //sh "fastlane test"
            }
        }
    }

    post {
        success {
//            archiveArtifacts artifacts: "app/build/outputs/apk/*.apk", fingerprint: true
            echo "Build succeeded."
        }

        failure {
            echo "Build failed."
        }
    }
}
