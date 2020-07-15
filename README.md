# madcamp-week1
2020년도 여름학기 카이스트 몰입캠프 1주차 공통과제

[윤하나, 이주훈]

# Mood Tracker #


## 탭 기능: ##
### 1. 연락처 (Contacts) ###
* 기기 연락처 내 정보 (Name, Phone Number, Email, Nickname)을 가져옴. 연락처를 누르면 기타 정보 (Email, Nickname)이 큰 화면에 나타남. 각 연락처 옆 전화번호 아이콘을 눌러 바로 전화를 걸 수 있음. 연락처 추가 가능.
### 2. 사진첩 (Gallery) ###
* 기기 사진첩에서 찍힌 순서대로 사진을 불러와 GridView로 나타냄. 탭을 하면 확대되고 다시 탭하면 축소됨.
### 3. 무드트래커 (Mood Tracker) ###
* 캘린더가 존재하고 캘린더의 날짜를 고른 뒤 그 날 기분 (Mood)를 기록함. (옵션: Joy, Sadness, Anger, Neutral, Not Chosen) 갤러리에서 사진을 불러와 사용 가능하며 짧은 일기를 쓸 수 있음. 캘린더에 각 기분에 따라 색깔이 기록되고 기록한 날짜를 누르면 이전에 기록한 내용이 나옴. 

## 탭 구현: ##
* ViewModel을 사용하여 SectionsPagerAdapter를 통해 Tab 사이에 swiping 밑 움직임이 가능함. TabLayout을 통해 Tab 구조를 구성함.
* SplashScreen: 처음 앱에 들어가면 뜨는 페이지로 필요한 권한 (Read_external_storage, contact 등)의 permission을 받음. 권한을 허락해주지 않으면 앱이 자동적으로 종료됨.

### 1. 연락처 (Contacts) ###
* Tab1: User에게 Permission을 받아 Intent를 사용하여 기기 내 Contact에서 연락처를 받아와서 RecyclerView로 나타냄. FragmentManager와 Transaction을 통하여 다른 탭으로 넘어가면 Refresh 됨.
* Person: 각 연락처의 정보를 담고 있는 Java Object.
* ContactDetailsFragment: 각 연락처를 누르면 나타나는 기타 정보가 담긴 큰 화면을 위한 Fragment.
* ContactListAdapter: RecyclerView를 위한 Adapter.
* AddFragment: Floating Action Button을 누르면 생성되는 새로운 연락처를 넣기위한 EditText가 있는 Fragment. ContactList에 새로운 Person을 만들어 넣고 JSON 파일에 write를 함. 
* JSONParsing 사용하여 PersonList를 저장했기 때문에 앱을 종료해도 다시 열면 이전 추가한 연락처가 기록되어 있음.

### 2. 사진첩 (Gallery) ###
* Tab2: Gallery에서 사진을 받아와서 GridView로 나타냄. Animator를 사용하여 누르면 이미지가 커지고 그 이미지를 다시 누르면 이미지가 작아짐.
* GalleryAdapter: 갤러리 구성을 위한 Adapter.

### 3. 무드트래커 (Mood Tracker) ###
* Tab3: JSON을 사용하여 달력을 구현함. MoodList_Item을 만들어 GridViewLayout이 있는 RecyclerView에 Adapter를 사용하여 넣음. 위에 있는 두 버튼을 통하여 달을 바꿀 수 있음. DateFormat을 통하여 각 날짜의 요일을 구함. 가장 먼저 뜨는 월은 현재 월임.
* Mood: 각 날짜의 정보를 담고 있는 Java Object.
* MoodDetailFragment: 각 날짜를 누르면 이전에 기록한 정보가 나오는 Fragment.
* MoodListAdapter: RecyclerView를 위한 Adapter.
* MoodEditFragment: 아무 기록없는 날짜를 누르면 새로운 Mood를 넣기위한 Spinner/EditText/ImageView가 있는 Fragment. Spinner를 사용하여 각 Mood를 정하고 결정된 Spinner에 따라 layout 색깔이 변함. <joy: 노란색, sadness: 파란색, anger: 빨간색, neutral: 회색, not chosen: 흰색)
