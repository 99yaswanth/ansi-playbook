pipeline{
    agent any
    parameters{
        string(name: 'BRANCH', defaultValue: 'master', description: '')
        string(name: 'serverip', defaultValue: '', description: '')
         string(name: 'BUILD_NUMBER', defaultValue: '', description: 'give build num')

    }
    stages{
        stage("download artifacts from s3"){
            steps{
                println "download artifacts"
                sh """
                aws s3 ls
                aws s3 cp s3://yashwanth24/${BRANCH}/hello-${BUILD_NUMBER}.war .
                """

            }
        }
        stage("copy artifacts"){
            steps{
                println "copying artifacts to tomcat"
                sh "scp -o StrictHostKeyChecking=no -i /tmp/nvirginia1.pem hello-${BUILD_NUMBER}.war ec2-user@${serverip}:/tmp"
                sh "ssh -i /tmp/nvirginia1.pem ec2-user@${serverip} \"sudo cp /tmp/hello-${BUILD_NUMBER}.war /var/lib/tomcat/webapps\""
            }
        }
    }
}