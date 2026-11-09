# Test Plan: Salesforce Login (https://login.salesforce.com/?locale=in)

| Field | Value |
|---|---|
| **Document ID** | TP-SFDC-LOGIN-001 |
| **Application Under Test (AUT)** | Salesforce Login Page |
| **URL** | https://login.salesforce.com/?locale=in |
| **Version** | 1.0 |
| **Prepared By** | QA Team |
| **Reviewed By** | QA Lead / Project Stakeholder |
| **Status** | Draft |

## 1. Objective

The objective of this test plan is to validate the functionality, usability, security posture (from a UI/behavior perspective), reliability, and compatibility of the Salesforce login page for the `locale=in` experience. Testing will confirm that:

- Authorized users can authenticate successfully using valid credentials and reach the intended Salesforce landing experience.
- Unauthorized or invalid access attempts are handled correctly with clear, actionable error messaging.
- Core UI controls (username, password, Login, Remember Me, links) behave as expected across supported environments.
- Defects are identified early, prioritized, and reported with sufficient evidence for timely resolution.

## 2. Scope

### 2.1 In Scope

| Area | Details |
|---|---|
| **Login functionality** | Valid login, invalid login, empty fields, incorrect password, incorrect username, credential format variations (as permitted by test data policy). |
| **UI validation** | Presence, visibility, labels/placeholders (where applicable), alignment/basic layout, enabled/disabled states, tab order, and default focus. |
| **Remember Me** | Checkbox selection/deselection behavior, persistence expectations per Salesforce behavior and test environment policy. |
| **Error handling** | Error message display, clarity, placement, and consistency for failed login attempts; no sensitive data leakage in messages. |
| **Navigation & links** | “Forgot Your Password?”, custom domain/login-related links (verification of navigation/availability as applicable), locale-specific behavior (`locale=in`). |
| **Session/redirect behavior** | Post-login redirect to authorized Salesforce area (valid credentials) and retention on login page for invalid attempts. |
| **Non-functional (UI-level)** | Basic performance observations (page load responsiveness), basic accessibility checks (keyboard access, labels), and cross-browser smoke on supported browsers. |
| **Automation coverage** | Selenium + Java + Maven + TestNG framework (POM/PageFactory, XPath locators) for regression/smoke candidates, as implemented in `Chapter2/`. |

### 2.2 Out of Scope

| Area | Rationale |
|---|---|
| **Backend/internal Salesforce services** | No access to Salesforce internal systems, databases, or source code; testing is black-box UI/behavioral. |
| **MFA/SSO/Identity provider internals** | Unless explicitly enabled and test accounts/environments are provided. |
| **Load/stress testing at scale** | Requires controlled environment, approvals, and tooling beyond UI functional automation. |
| **Penetration testing** | Requires separate security engagement, authorization, and specialized tools/processes. |
| **Third-party integrations** | Beyond what is directly exposed on the login page. |
| **Mobile native app testing** | This plan covers the web login URL unless mobile web scope is added. |

## 3. Test Strategy

### 3.1 Test Approach

- **Black-box testing** based on observable UI behavior and expected authentication outcomes.
- **Risk-based prioritization**: valid login, invalid login/error handling, and critical UI controls are tested first.
- **Layered execution**:
  - **Smoke tests**: verify page availability and critical login controls on each build/environment check.
  - **Functional tests**: detailed positive/negative scenarios, boundary inputs, and UI validations.
  - **Regression tests**: rerun automated and key manual cases after changes or environment updates.
  - **Exploratory testing**: time-boxed sessions to uncover locale, usability, and edge-case issues not covered by scripted cases.

### 3.2 Test Types

| Type | Description | Method |
|---|---|---|
| Functional | Login success/failure, Remember Me, error messages, redirects | Manual + Automated (TestNG) |
| UI/Usability | Field visibility, button behavior, basic layout, messages | Manual |
| Compatibility | Supported browsers/OS combinations | Manual smoke + targeted automation |
| Accessibility (basic) | Keyboard navigation, focus visibility, form labels | Manual checklist |
| Security (behavioral) | Generic error messaging, no credential exposure in UI, session/redirect sanity | Manual + Automated assertions |
| Localization sanity | `locale=in` page load and key elements/messages presence | Manual |

### 3.3 Test Design Techniques

- **Equivalence Partitioning**: valid credentials vs. invalid credentials vs. empty inputs.
- **Boundary Value Analysis**: empty strings, long inputs (within safe test limits), special characters (policy-compliant).
- **Decision Table**: combinations of username/password/Remember Me and expected outcomes.
- **Error Guessing**: common user mistakes (leading/trailing spaces if applicable, caps lock scenarios noted manually), repeated failures observation (subject to policy).

### 3.4 Automation Strategy

| Item | Detail |
|---|---|
| **Framework** | Selenium WebDriver, Java, Maven, TestNG (`Chapter2/pom.xml`, `Chapter2/testng.xml`) |
| **Design pattern** | Page Object Model with PageFactory and reusable action methods (`LoginPage`, `BaseTest`) |
| **Locator strategy** | **XPath only** (per project constraint) |
| **Synchronization** | `WebDriverWait` / explicit waits; no `Thread.sleep()` |
| **Data management** | TestNG `@Parameters`, `@DataProvider`, and system properties for credentials/URLs (valid credentials supplied externally; never hardcode secrets in VCS) |
| **Execution** | `mvn test` from `Chapter2`; CI integration via Maven Surefire/TestNG suite |
| **Candidates for automation** | Smoke (page load, controls present), invalid login matrix, valid login happy path (with secure credential injection) |
| **Not automated (initially)** | Visual aesthetics, subjective usability, complex MFA/SSO flows without stable test hooks/accounts |

### 3.5 Entry Criteria

- Test environment/URL is accessible: https://login.salesforce.com/?locale=in
- Test data available (at minimum: invalid credential sets; valid credentials provided securely for valid-login execution).
- Supported browser(s) and WebDriver/Chrome setup available.
- Automation framework builds successfully (`mvn -q -DskipTests` or equivalent).
- Test plan/cases reviewed and approved (for formal cycles).

### 3.6 Exit Criteria

- Planned test cases executed (or formally deferred with approval).
- Critical/Blocker defects resolved or have an approved workaround/risk acceptance.
- Test summary report prepared with pass/fail, defect status, and open risks.
- Automation smoke suite passes in the agreed environment (where credentials/environment permit).

### 3.7 Suspension & Resumption Criteria

- **Suspend**: AUT unavailable, blocking environment/network issues, critical defect preventing meaningful progress, or missing required credentials/access.
- **Resume**: Environment restored, blocker fixed/workaround provided, or required access/data supplied.

## 4. Test Environment

| Component | Specification (example; confirm per project) |
|---|---|
| **Application** | https://login.salesforce.com/?locale=in |
| **OS** | Windows 10/11 (primary), macOS/Linux as needed |
| **Browsers** | Google Chrome (latest stable); optionally Edge/Firefox for compatibility smoke |
| **Automation** | Java 11+, Maven, Selenium 4.x, TestNG 7.x, ChromeDriver compatible with Chrome |
| **Network** | Stable internet; corporate proxy/VPN considerations documented if applicable |

## 5. Test Deliverables

- Test Plan (this document)
- Test scenarios/cases (manual and/or automated)
- Automated scripts (`Chapter2/src/...`) and TestNG suite (`Chapter2/testng.xml`)
- Defect reports (tool TBD: Jira/Azure DevOps/etc.)
- Test execution report / summary
- Traceability mapping (requirements/risk → test cases) as needed

## 6. Risks

| ID | Risk | Likelihood | Impact | Mitigation |
|---|---|---|---|---|
| R1 | **Valid credentials unavailable/rotated**; valid-login automation skipped or fails. | Medium | High | Use secure credential injection (CI secrets/TestNG params/system properties); maintain a dedicated non-production test user where policy allows; mark valid-login as conditional with clear skip reason. |
| R2 | **Salesforce UI/DOM changes** (A/B testing, locale updates) break XPath locators. | Medium | Medium | Use stable XPath anchors where possible; keep locators centralized in POM; run smoke regularly; allocate maintenance time. |
| R3 | **MFA/CAPTCHA/anti-bot controls** triggered during automation. | Medium | High | Coordinate test accounts/settings with Salesforce admin; avoid aggressive repeated runs; document as environment constraint; manual verification fallback. |
| R4 | **Environment/network instability** causes false failures. | Medium | Medium | Retry strategy at CI level (not `Thread.sleep`); capture screenshots/logs; distinguish infra issues from product defects. |
| R5 | **Security/compliance constraints** on testing production login. | Medium | High | Obtain approvals; follow organizational security policy; avoid storing secrets in code/repo; use approved test tenants when available. |
| R6 | **Locale-specific behavior (`locale=in`)** differences missed. | Low | Medium | Include locale sanity checks; verify key labels/messages and redirects; compare with baseline locale if required. |
| R7 | **Insufficient time/resources** for full coverage. | Medium | Medium | Risk-based prioritization; automate high-value regressions; communicate scope trade-offs early. |

## 7. Overall Resource Allocation

| Role | Count (indicative) | Responsibilities | Allocation |
|---|---|---|---|
| **QA Lead / Mentor** | 1 | Test strategy, plan approval, risk management, stakeholder communication, quality gates | 10–20% across cycle; higher during planning/review/sign-off |
| **Senior QA Automation Engineer** | 1 | Framework design/maintenance (POM, BaseTest), CI integration, code reviews, complex scenario automation | 40–60% during automation sprint; 10–20% maintenance |
| **QA Automation Engineer(s)** | 1–2 | Implement/execute automated scripts, data providers, defect verification, reporting | 50–80% during execution |
| **Manual QA Engineer** | 1 | Exploratory testing, UI/accessibility checks, locale/cross-browser validation, defect logging | 30–50% during functional cycle |
| **Salesforce Admin / App Owner** (support) | As needed | Test accounts, MFA/SSO configuration, environment clarifications | On-demand |
| **DevOps / Environment Support** (support) | As needed | CI runners, browser/grid setup, network/proxy access | On-demand |

### 7.1 Estimated Effort (indicative)

| Activity | Effort |
|---|---|
| Test planning & review | 0.5–1 day |
| Test scenario/case design | 1–2 days |
| Automation enhancement (framework + cases) | 2–4 days (depending on scope/MFA constraints) |
| Manual functional + exploratory execution | 1–2 days |
| Regression/smoke execution & reporting | 0.5–1 day per cycle |

*Effort assumes stable access and available test data; adjust for MFA/SSO, additional locales/browsers, or CI setup.*

## 8. Schedule & Milestones (template)

| Milestone | Target | Owner |
|---|---|---|
| Test plan approved | TBD | QA Lead |
| Test cases/scenarios ready | TBD | QA Team |
| Automation smoke ready | TBD | Automation Engineer |
| Test execution complete | TBD | QA Team |
| Test summary/sign-off | TBD | QA Lead / Stakeholders |

## 9. Approvals

| Name | Role | Signature | Date |
|---|---|---|---|
| | QA Lead | | |
| | Project Manager / Product Owner | | |
| | Stakeholder | | |
