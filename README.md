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

## AI тусламж

OpenAI Codex-ийг DU шинжилгээ, тест бичих, тест ажиллуулах болон тайлангийн
найруулга, GitHub-д байршуулахад ашиглав. Хамралт, мутацийн үр дүнг бодит
Maven гаралттай тулгана.
