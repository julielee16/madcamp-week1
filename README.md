# madcamp-week1
2020년도 여름학기 카이스트 몰입캠프 1주차 공통과제

[윤하나, 이주훈]

# Mood Tracker #


## 탭 기능: ##
### 1. 연락처 (Contacts) ###
* 기기 연락처 내 정보 (Name, Phone Number, Email, Nickname)을 가져옴. 각 연락처 옆 전화번호 아이콘을 눌러 바로 전화를 걸 수 있음. 연락처 추가 가능.
### 2. 사진첩 (Gallery) ###
* 기기 사진첩에서 찍힌 순서대로 사진을 불러옴. 탭을 하면 확대되고 다시 탭하면 축소됨.
### 3. 무드트래커 (Mood Tracker) ###
* 캘린더가 존재하고 캘린더의 날짜를 고른 뒤 그날 기분 (Mood)를 기록하고 (옵션: Joy, Sadness, Anger, Neutral, Not Chosen), 갤러리에서 사진을 불러와 사용가능하며 짧은 일기를 쓸 수 있음. 캘린더에 각 기분에 따라 색깔이 기록되고 기록한 날짜를 누르면 기록한 내용이 나옴. 

## 탭 구현: ##
### 1. 연락처 (Contacts) ###
* Tab1: User에게 Permission을 받아 기기 내 Contact에서 연락처를 받아와서 RecyclerView로 나타냄. FragmentManager와 Transaction을 통하여 다른 탭으로 넘어가면 Refresh 됨.
* ContactDetailsFragment: 
* ContactListAdapter:

### 2. 사진첩 (Gallery) ###
splash screen: 처음 앱에 들어가면 뜨는 페이지로 필요한 권한 (Read_external_storage, contact 등)의 permission을 받음 권한을 허락해주지 않으면 앱이 자동적으로 종료됨
tab2: gallery에서 사진을 받아와서 gridview로 나타냄. 이미지를 누르면 이미지가 커지고 그 이미지를 다시 누르면 이미지가 작아짐

### 3. 무드트래커 (Mood Tracker) ###
tab3: json사용하여 달력을 구현하고 달력의 각 날짜를 누르면 그 날의 기분, 사진, 짧은 일기를 쓸 수 있음
