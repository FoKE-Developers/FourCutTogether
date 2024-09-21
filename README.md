# 같이네컷
- Last updated: 2024-08-13

## Specification

### Android
- targetSdk `35`
- minSdk `31`
- DataBinding for UI
- Clean Architecture based

### Contributor
- Dokyoon Kim (DokySp)
- Yoonsoo Kim (ing03201)

## How to work with others
1. `Issues`에 새로운 이슈를 생성한다.
   - Assignee: 실제 작업하는 사람
   - Labels: 해당하는 라벨 추가 **반드시 하나의 라벨만 추가할 것**
   - Projects: `FourCutsTogether - Android`
   - Milestone: 첫 버전 배포 전까지 `Phase 1` 사용
2. 코드 작업 후, PR 및 리뷰를 요청한다.
   - 자세한 내용은 아래 `PR Rules`를 따른다

## PR Rules
- `main` branch 만을 두고 개발한다.
- 타인의 리뷰를 받기 전 merge는 금지된다.
- build pass 이전에 merge는 금지된다.
- PR 요청 시, **Description에 위에서 생성한 Issue number를 반드시 기입한다.**
- PR 요청 시, 아래 속성을 채운다.
   - Reviewers: 자신을 제외한 모든 사람
   - Assignee: 자신
   - Labels: 해당하는 라벨 추가 **반드시 하나의 라벨만 추가할 것**
   - Projects: 기입 X
   - Milestone: 기입 X
- Rebase로 merge **Squash 금지!!**

## Code Review Rules
- PR 요청 후, slack에 리뷰 요청
- 리뷰에 대한 comment는 반드시 해당 PR에 comment로 남기고 다른 채널에서는 진행하지 않는다

## Dependencies
- 프로젝트 및 관련 버전은 libs.versions.toml 에서 관리
- 기타 의존성은 사용한 모듈에서 관리