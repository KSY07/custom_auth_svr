pipeline {
    agent any

    environment {
        previousVersion = '1.0'
        version = '1.0'
        DOCKER_HUBCREDENTIAL = credentials('ksy_docker_credentials')
    }

    stages {
        stage('기존 컨테이너 정지') {
            steps {
                script {
                    try { sh 'docker stop scarf_auth && docker rm scarf_auth' }
                    catch (e) { echo "Container Doesn't exist Log: {e}"}
                }
            }
        }
        stage('소스 코드 업데이트') {
            steps {
                git branch: 'deployBiz', credentialsId: '3e0c9f1b-ccfa-4214-805b-8d2466a7c177', url:'http://172.16.10.56:7777/ksy2008w/scarf_auth'
            }
        }
        stage('Docker 파일 빌드') {
            agent {
                dockerfile {
                    filename 'Dockerfile'
                    additionalBuildArgs "-t ksy2008w/scarf_auth:${version}"
                }
            }
            steps {
                echo '################################# SCARF_AUTH SVR Build Complete######################################'
            }
        }
        stage('도커 컨테이너 실행') {
            steps {
                sh "docker run -i -t -d -p 9090:9090 --memory 400m --name scarf_auth ksy2008w/scarf_auth:${version}"
                echo "###################################### SCARF_AUTH_SVR Init Complete###############################"
            }
        }
    }
}