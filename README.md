# Лаборатори 10 Өгөгдлийн урсгалын тест

**Нэр:** Ган-Оргил  
**Оюутны код:** B232270091  
**Хичээл:** F.CSA313 Программ хангамжийн чанарын баталгаа ба тест

`DataFlowTargets.java` эх кодын definition–use хосуудыг def-clear замаар
шинжилж, JUnit тестээр хамруулна. Эх кодын мөрийн дугаарлалтыг starter
хувилбараар авсан. Predicate-ийн use-ийг гарах T/F ирмэгээр нь тусад нь
тоолно. `d -> u` замд тухайн хувьсагчийг дахин тодорхойлоогүй байна;
`streak = streak + 1` зэрэг мөрийн RHS use нь LHS definition-ээс өмнө болно.

## Орчин

```text
java -version
openjdk version "17.0.21" 2026-10-20
OpenJDK Runtime Environment Temurin-17.0.21+9 (build 17.0.21+9)
OpenJDK 64-Bit Server VM Temurin-17.0.21+9 (build 17.0.21+9, mixed mode, sharing)

mvn -version
Apache Maven 3.9.16 (2bdd9fddda4b155ebf8000e807eb73fd829a51d5)
Maven home: C:\Users\enhta\Desktop\lab9\.lab10-work\tools\apache-maven-3.9.16
Java version: 17.0.21, vendor: Eclipse Adoptium, runtime: C:\Users\enhta\Desktop\lab9\.lab10-work\tools\jdk-17.0.21+9
Default locale: en_US, platform encoding: UTF-8
OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
```

Maven Compiler Plugin-ийг 3.15.0 дээр тогтоосон тул хуучин Maven ч `release=17` тохиргоог хэрэглэнэ. `src/main` нь Starter-тэй ижил.

## A gcd

### Definition ба use

```text
13: def x, y                 // параметрийн эхний утгууд
14: int tmp                  // утга оноогоогүй тул definition биш
15: p-use y                  // T: 16 руу, F: 20 руу
16: c-use x, y; def tmp       // RHS-ийг уншсаны дараа tmp-д онооно
17: c-use y; def x
18: c-use tmp; def y
20: c-use x
```

### Бүх боломжит DU хос

| ID | Хувьсагч | Def мөр | Use мөр | Төрөл | Def-clear зам | Тестийн ID | Хамрагдсан |
|---|---|---:|---|---|---|---|---|
| GDU01 | x | 13 | 16 | c | 13 → 15T → 16 | G2, G3 | Тийм |
| GDU02 | x | 13 | 20 | c | 13 → 15F → 20 | G1 | Тийм |
| GDU03 | x | 17 | 16 | c | 17 → 18 → 15T → 16 | G2 | Тийм |
| GDU04 | x | 17 | 20 | c | 17 → 18 → 15F → 20 | G2, G3 | Тийм |
| GDU05 | y | 13 | 15T | p | 13 → 15T | G2, G3 | Тийм |
| GDU06 | y | 13 | 15F | p | 13 → 15F | G1 | Тийм |
| GDU07 | y | 13 | 16 | c | 13 → 15T → 16 | G2, G3 | Тийм |
| GDU08 | y | 13 | 17 | c | 13 → 15T → 16 → 17 | G2, G3 | Тийм |
| GDU09 | y | 18 | 15T | p | 18 → 15T | G2 | Тийм |
| GDU10 | y | 18 | 15F | p | 18 → 15F | G2, G3 | Тийм |
| GDU11 | y | 18 | 16 | c | 18 → 15T → 16 | G2 | Тийм |
| GDU12 | y | 18 | 17 | c | 18 → 15T → 16 → 17 | G2 | Тийм |
| GDU13 | tmp | 16 | 18 | c | 16 → 17 → 18 | G2, G3 | Тийм |

| ID | Бодит тестийн метод | Оролт | Expected | Зорилго |
|---|---|---|---:|---|
| G1 | `zeroSecondReturnsFirst` | gcd(7, 0) | 7 | Давталтгүй зам |
| G2 | `twoIterationsCoverRedefinitions` | gcd(12, 8) | 4 | Давталтын definition дараагийн давталтад хүрэх |
| G3 | `zeroFirstBecomesSecond` | gcd(0, 5) | 5 | Эхний параметр тэг байх тохиолдол |

`gcd(7, 0)` нь эхний `x`-ийг өөрчлөлтгүй return-д хүргэх **GDU02** болон
эхний `y`-ийн **GDU06** false ирмэгийг хамардаг. Давталт орвол 17-р мөр
`x`-ийн 13-р мөрийн definition-ийг kill хийнэ. `gcd(12, 8)` нь
`(12,8) → (8,4) → (4,0)` гэсэн хоёр давталттай тул GDU03, GDU09,
GDU11, GDU12-ыг хамарна. G1 ба G2 нийлээд хүснэгтийн бүх хосыг хамардаг;
G3 нь оролтын нэмэлт шалгалт юм. Гаралт: [results/mvn-verify-gcd.txt](results/mvn-verify-gcd.txt).

## B bonus эхний шат ALL DEFS

`BonusTest.java`-д `allDefsScoreThree` (B1) ба `allDefsScoreFive` (B2) гэсэн
**2 тест** all-defs хангана. Gcd-ийн 3 тесттэй нийлээд Maven **5 тест** ажиллуулна.

| Definition | Use-д хүргэх тест ба зам |
|---|---|
| attendance 31 | B1: 32aF, 38 init, 38T/F |
| threshold 31 | B1: 32bF, 50F; B2: 50T |
| a 38 | B1: 39T/F |
| present 35 | B1: эхний 40-ийн RHS |
| present 40 | B1: дараагийн 40-ийн RHS, эцэст нь 50F |
| streak 36 | B1: эхний 41-ийн RHS |
| streak 41 | B1: 42T/F, 43, дараагийн 41-ийн RHS |
| streak 46 | B1: тасалсны дараах 41-ийн RHS |
| best 37 | B1: эхний 42T |
| best 43 | B1: дараагийн 42T/F, 53T |
| score 49 | B1: 49 → 50F → 53T → 54 |
| score 51 | B2: 51 → 53F → 56 |
| score 54 | B1: 54 → 56 |

Нэг тест хүрэлцэхгүй. 49-р мөрийн `score` use-д хүрэх цорын ганц боломж нь
50F дараа 53T болж 54-ийн RHS-д уншигдах, эсвэл 50F ба 53F болж 56-д
уншигдах явдал юм. Харин 51-ийн definition-д хүрэхэд 50T байх ёстой;
51 нь 49-ийг use-ээс нь өмнө kill хийнэ. Иймээс score 49 ба 51-ийн
definition-үүдийг нэг гүйцэтгэлд хоёуланг нь use-д хүргэх боломжгүй, доод
хязгаар нь 2. Дээрх B1 ба B2 бусад definition-ийг мөн use-д хүргэж байгаа
тул энэ доод хязгаар биелсэн.

B1-ийн оролт `[1,1,1,1,1,0,1], threshold=8` тул present=6, best=5,
expected=3. B2-ийн `[0,1], threshold=1` тул present=1, best=1, expected=5.
Гаралт: [mvn-verify-alldefs.txt](results/mvn-verify-alldefs.txt),
[jacoco-alldefs.csv](results/jacoco-alldefs.csv).

Энэ шатанд DataFlowTargets-ийн branch нь **14/16**, line нь **22/24**.
Null/сөрөг threshold-ийн true ирмэг болон 33-р мөрийн throw хараахан
хамрагдаагүй. 7-р мөрийн далд constructor мөн хамрагдаагүй байна.
All-defs хангагдсан нь branch 100% гэсэн үг биш.


## AI тусламж

OpenAI Codex-ийг DU шинжилгээ, тест бичих, тест ажиллуулах болон тайлангийн
найруулга, GitHub-д байршуулахад ашиглав. Хамралт, мутацийн үр дүнг бодит
Maven гаралттай тулгана.
