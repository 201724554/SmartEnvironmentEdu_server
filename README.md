# 스마트 환경 교육 시스템 백엔드
## 개발 목적
* 다양한 센서를 부착한 아두이노 기반의 기기에서 실시간으로 전송하는 환경 데이터를 웹 상에서 볼 수 있도록 하는 플랫폼을 기기와 연결하고, 데이터를 저장하기 위한 서버

## 주요 기능
* 사용자를 학생과 교사로 구분해 프론트엔드에서 교사가 학생의 기기에서 전송되는 정보를 볼 수 있도록 설계
* WebSocketMessageBroker를 이용해 기기와 프론트엔드를 연결
* 사전에 등록된 MAC 주소를 가진 기기만 연결할 수 있도록 처리
* 사용자의 선택에 따라 기기에서 수신한 데이터를 저장할 수 있도록 API 구현

## 데이터 구조
<img src="https://user-images.githubusercontent.com/38274661/223510748-5b44d3a7-cd73-494e-8638-8f0c369f4d28.jpg" width="400" height="500"/>

## 아키택처
<img src="https://user-images.githubusercontent.com/38274661/223520137-5e090463-7ad0-4018-abb5-d6202fc75fd8.jpg" witdh="500" height="500"/>

