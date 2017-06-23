#!groovy​


pipeline {
    agent { 
        docker {
            image "uptech/android-ci"
            label "docker"
            args "-u root"
        }
    }

    environment {
        POMODORO_KEYSTORE_ALIAS = credentials("POMODORO_KEYSTORE_ALIAS")
        POMODORO_KEYSTORE_PASSWORD = credentials("POMODORO_KEYSTORE_PASSWORD")
    }


    stages {
        stage("Checkout") { steps { checkout scm } }

        stage("Test") {
            steps {
                sh "fastlane test"
            }
        }

        stage("QA Deploy") {
//            when { branch "develop"}

            steps {
                sh "fastlane beta"
            }
        }
    }

    post {
        success {
            archiveArtifacts artifacts: "app/build/outputs/apk/*.apk", fingerprint: true
            echo "Build succeeded."
        }

        failure {
            echo "Build failed."
        }
    }
}
