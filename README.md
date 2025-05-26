# 🎉 서비스 소개

<div align="center">
  <h3><a href="https://study.genieus.shop/" style="color:purple">Genieus Study 바로가기</a></h3>
 <img src="https://github.com/user-attachments/assets/2c870fe9-4802-454c-bc7e-fcbe2fb79daa" width="150" height="150" />

☑️ 매일 **출석 체크**하는 게 번거롭지 않으신가요?

☑️ 오늘 뭘 **공부**했는지 기록하기 어렵지 않으셨나요?

☑️ **스터디원들과 함께** 동기부여 받고 싶지 않으신가요?

<h4>스터디원들과 함께 성장하는 출석 관리 및 학습 인증 플랫폼 📚</h4>

</div>

# 　

**Genieus Study**는 스터디원들이 함께 성장할 수 있도록 돕는 종합 학습 관리 플랫폼입니다.

스터디를 하다 보면 각자의 학습 진도나 출석 현황을 파악하기 어려운 경우가 많습니다. Genieus Study가 `체계적인 출석 관리와 다양한 학습 활동 인증`을 통해 이를 해결하려고 합니다.

<br/><br/>

## 주요 기능

<table>
<tr>
<th width="50%">👥 사용자 관리 & 인증</th>
<th width="50%">🔔 실시간 알림 시스템</th>
</tr>
<tr>
<td valign="top">

* JWT 기반 액세스/리프레시 토큰 분리 인증 시스템
* Redis를 활용한 토큰 블랙리스트 및 캐싱 관리
* 회원가입/로그인 및 계정 상태 관리 시스템
* ### → [📝 Docs](https://github.com/Genie-Uss/genieus-study-server/wiki/auth-system)

</td>
<td valign="top">

* Discord 웹훅을 통한 출석/퇴실 실시간 알림
* 학습 인증 활동 자동 공유 및 동기부여 촉진
* 스케줄러 기반 출석 독려 및 코어 시간 종료 알림
* ### → [📝 Docs](https://github.com/Genie-Uss/genieus-study-server/wiki/notification-system)

</td>
</tr>
<tr>
<th>📅 출석 관리</th>
<th>📊 학습 목표 관리</th>
</tr>
<tr>
<td valign="top">

* 지각 체크 및 목표 달성률 자동 계산
* 스케줄러 기반 자동 퇴실 배치 처리
* 희망 출석 시간 및 코어 시간 개인화 설정
* ### → [📝 Docs](https://github.com/Genie-Uss/genieus-study-server/wiki/attendance-system)

</td>
<td valign="top">

* 일별 학습 목표 설정 및 달성률 추적
* ### → [📝 Docs](https://github.com/Genie-Uss/genieus-study-server/wiki/learning-goal-system)

</td>
</tr>
<tr>
<th colspan="2">🎯 학습 인증 시스템(도장 시스템)</th>
</tr>
<tr>
<td colspan="2" valign="top">

* 코딩테스트, TIL, 구직활동 도장을 통한 체계적 학습 기록
* 출석 여부 검증을 통한 인증 도장 생성 제한
* 카테고리별 학습 활동 분류 및 통계
* ### → [📝 Docs](https://github.com/Genie-Uss/genieus-study-server/wiki/stamp-system)

</td>
</tr>
</table>

<br/><br/>

## 🛠 적용 기술

<table>
<tr>
<th width="50%">📁 Backend</th>
<th width="50%">🌐 Infrastructure</th>
</tr>
<tr>
<td valign="top">

![JDK 17](https://img.shields.io/badge/JDK%2017-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot%203.4.5-6DB33F?logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?logo=springsecurity&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?logo=spring&logoColor=white)
![Spring WebFlux](https://img.shields.io/badge/Spring%20WebFlux-6DB33F?logo=spring&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?logo=jsonwebtokens&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?logo=gradle&logoColor=white)

</td>
<td valign="top">

![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white)
![Docker Compose](https://img.shields.io/badge/Docker%20Compose-2496ED?logo=docker&logoColor=white)
![Google Cloud Platform](https://img.shields.io/badge/Google%20Cloud%20Platform-4285F4?logo=googlecloud&logoColor=white)
![Artifact Registry](https://img.shields.io/badge/Artifact%20Registry-4285F4?logo=googlecloud&logoColor=white)

</td>
</tr>
<tr>
<th>🗄️ Database</th>
<th>🚀 CI/CD & Monitoring</th>
</tr>
<tr>
<td valign="top">

![MySQL](https://img.shields.io/badge/MySQL%208.0-4479A1?logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?logo=redis&logoColor=white)

</td>
<td valign="top">

![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF?logo=githubactions&logoColor=white)
![Logstash](https://img.shields.io/badge/Logstash-005571?logo=elastic&logoColor=white)
![JSON Logging](https://img.shields.io/badge/JSON%20Logging-000000?logo=json&logoColor=white)

</td>
</tr>
<tr>
<th>🔔 External Integration</th>
<th>🤝 Collaboration</th>
</tr>
<tr>
<td valign="top">

![Discord Webhook](https://img.shields.io/badge/Discord%20Webhook-5865F2?logo=discord&logoColor=white)

</td>
<td valign="top">

![GitHub](https://img.shields.io/badge/GitHub-181717?logo=github&logoColor=white)
![Discord](https://img.shields.io/badge/Discord-5865F2?logo=discord&logoColor=white)

</td>
</tr>
</table>

<br/><br/>

## ✔️ 서비스/프로젝트 목표

<table>
<tr>
<td width="50%" valign="top">


**🔄 이벤트 기반 아키텍처로 느슨한 결합 구현**

* 도메인 이벤트와 Spring Event를 활용하여 출석, 도장 생성 등의 비즈니스 로직과 알림 처리를 분리했습니다.

**🏗️ 레이어드 아키텍처 기반 관심사 분리**

* Presentation, Application, Domain, Infrastructure 계층을 명확히 분리하고, 의존성 역전 원칙을 적용한 구조로 도메인 엔티티와 값 객체를 통해 비즈니스 로직을 캡슐화하여 외부
  의존성에 영향받지 않도록 설계했습니다.

**🔐 JWT 기반 인증/인가 시스템으로 보안성 확보**

* 액세스/리프레시 토큰 분리와 Redis 기반 토큰 관리로 안전하고 확장 가능한 인증 시스템을 구축했습니다.

</td>
<td width="50%" valign="top">

**💬 Discord 웹훅을 통한 실시간 협업 환경 조성**

* 출석, 학습 인증 등의 활동을 실시간으로 공유하여 스터디원들 간의 동기부여와 상호작용을 촉진합니다.

<br/>

**⏰ 스케줄러 기반 자동화 시스템으로 운영 효율성 향상**

* 자동 퇴실 처리와 알림 발송을 통해 관리자의 개입 없이도 안정적인 서비스 운영이 가능합니다.

**☁️ GCP 기반 CI/CD 파이프라인 구축으로 안정적 배포**

* GitHub Actions와 Google Cloud Platform을 활용한 자동화된 배포 시스템으로 지속적인 서비스 개선과 안정성을 보장합니다.
* #### [📝 Docs](https://github.com/Genie-Uss/genieus-study-server/wiki/dev)

</td>
</tr>
</table>

<br/><br/>

## 🏗 시스템 아키텍처

![system architecture](https://github.com/user-attachments/assets/3aff7051-2dd8-454f-a467-963c28152736)



<br/><br/>

## 도메인 아키텍처

![domain architecture](https://github.com/user-attachments/assets/9d95265f-e1fd-46b8-826c-3fb15edd22a0)


<br/><br/>

## 👥 개발 인원 및 역할

<table width="100%">
<tr>
<th width="50%" align="center">
<div align="center">
<a href="https://github.com/Soobinnni">
<img src="https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png" width="20">
김수빈
</a>
</div>
</th>
<th width="50%" align="center">
<div align="center">
<a href="https://github.com/seongmin1117">
<img src="https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png" width="20">
최성민
</a>
</div>
</th>
</tr>
<tr>
<td width="50%" align="center">
<a href="https://github.com/Soobinnni">
<img src="https://github.com/Soobinnni.png" width="150">
</a>
</td>
<td width="50%" align="center">
<a href="https://github.com/seongmin1117">
<img src="https://github.com/seongmin1117.png" width="150">
</a>
</td>
</tr>
<tr>
<td width="50%" align="center">
<div align="center">
<a href="https://github.com/Genie-Uss/genieus/wiki/김수빈">📝 Docs</a>
</div>
</td>
<td width="50%" align="center">
<div align="center">
<a href="https://github.com/Genie-Uss/genieus/wiki/최성민">📝 Docs</a>
</div>
</td>
</tr>
<tr>
<td width="50%" align="center">
• 사용자 도메인 개발<br/>
• 출석 도메인 개발<br/>
• 인증 도메인 개발<br/>
• 알림 도메인 및 구조 설계<br/>
• GCP 배포 및 CI/CD
</td>
<td width="50%" align="center">
• 도장 도메인 개발
</td>
</tr>
</table>

<br/><br/>
<br/><br/>
<br/><br/>
