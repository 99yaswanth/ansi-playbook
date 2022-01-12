pipeline{
    agent any
    parameters{
        string(name: 'BRANCH', defaultValue: 'master' , description: '')

    }
    stages{
        stage("checkout code"){
            steps{
                println "clone a clode"
                git branch: "${BRANCH}",
                url: 'https://github.com/99yaswanth/boxfuse-sample-java-war-hello.git'
            }
        }
        stage("build code"){
            steps{
                println "build code"
                sh "mvn clean package"
                sh "ls -l"
            }
        }
        stage("upload to s3"){
            steps{
                sh "aws s3 cp target/hello-*.war s3://yashwanth24/${BRANCH}/"
            }
        }
    }
}