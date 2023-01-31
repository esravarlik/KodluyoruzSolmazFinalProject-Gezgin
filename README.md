# Gezgin
Kodluyoruz &amp; Solmaz Gümrük Müşavirliği kapsamında yapılan Bootcamp bitirme projesi. Online uçak ve otobüs bileti satışı yapan Java BackEnd Projesidir.


**Proje Konusu**  
Online uçak ve otobüs bileti satışı yapılmak istenmektedir. Uygulamanın gereksinimleri
aşağıdaki gibidir.  

---
![](gezgin-diagram.png)  

**Gereksinimler**  
• Kullanıcılar sisteme kayıt ve login olabilmelidir.  
• Kullanıcı kayıt işleminden sonra mail gönderilmelidir.  
• Kullanıcı şifresi istediğiniz bir hashing algoritmasıyla database kaydedilmelidir.  
• Admin kullanıcı yeni sefer ekleyebilir, iptal edebilir, toplam bilet satışını, bu satıştan
elde edilen toplam ücreti görebilir.  
• Kullanıcılar şehir bilgisi, taşıt türü(uçak & otobüs) veya tarih bilgisi ile tüm seferleri
arayabilmelidir.  
• Bireysel kullanıcı aynı sefer için en fazla 5 bilet alabilir.  
• Bireysel kullanıcı tek bir siparişte en fazla 2 erkek yolcu için bilet alabilir.  
• Kurumsal kullanıcı aynı sefer için en fazla 20 bilet alabilir.  
• Satın alma işlemi başarılı ise işlem tamamlanmalı ve asenkron olarak bilet detayları
kullanıcının telefona numarasına sms gönderilmeli.  
• SMS, mail ve push Notification gönderme işlemleri için sadece Database kayıt etme
işlemi yapılması yeterlidir. Fakat bu işlemler tek bir Servis(uygulama) üzerinden ve
polimorfik davranış ile yapılmalıdır.  
• Kullancılar aldığı biletleri görebilmelidir.


**Sistem Kabulleri**  
Kullanıcılar bireysel ve kurumsal olabilir.  
SMS, Mail ve Push Notification gönderim işlemleri Asenkron olmalıdır.  
Uçak yolcu kapasitesi: 189  
Otobüs yolcu kapasitesi: 45  
Ödeme şekli sadece Kredi kartı ve Havale / EFT olabilir.  
Ödeme Servisi işlemleri Senkron olmalıdır.
---
**Kullanılan Teknolojiler**
- Java 11
- Spring Boot
- PostgreSQL
- RabbitMQ
- Redis

---
**Entity Relationship Diagram (ERD)**
![](gezginERD.png)

---
Projede ADMIN ve User rolleri yer almaktadır. Bu rollere ait accessToken'ları kullanarak rollerin yetkilerine
göre endpointlere erişilebilmektedir.  
**Admin Request:**
``` json
{
    "email": "alpi@mail.com",
    "password":"password",
    "userType":"INDIVIDUAL",
    "phoneNumber":"8545345325",
    "role":"ADMIN"

}
```
```http
POST http://localhost:8080/register
```
Response: 200 OK
```json
{
  "id": 5,
  "email": "alpi@mail.com",
  "role": "ADMIN",
  "userType": "INDIVIDUAL",
  "phoneNumber": "8545345325"
}
```
Request:
``` json
{
    "email":"alpi@mail.com",
    "password":"password"
}
```
```http
POST http://localhost:8080/login
```
Response: 200 OK
```json
{
  "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbHBpQG1haWwuY29tIiwiaXNzIjoiam9qb2VzcmEiLCJleHAiOjE2NzUxODMwODF9.ffb_gbj5TG6ETfzFmFTmLxN-r7moTuN3MTvIo9pVBOY",
  "user": {
    "id": 5,
    "email": "alpi@mail.com",
    "role": "ADMIN",
    "userType": null,
    "phoneNumber": null
  }
}
```
~~Sadece ADMIN rolü erişebilir.  
**Authorization Type:**  Bearer Token  
Token: ``eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.
eyJzdWIiOiJhbHBpQG1haWwuY29tIiwiaXNzIjoiam9qb2VzcmEiLCJleHAiOjE2NzUxODMwODF9.
ffb_gbj5TG6ETfzFmFTmLxN-r7moTuN3MTvIo9pVBOY"``  
Request:
```
{
    "vehicleType": "AIRPLANE",
    "capacity": 189
}
```
```http
POST http://localhost:8080/vehicles
```
Response: 200 OK
```json
{
  "id": 6,
  "vehicleType": "AIRPLANE",
  "capacity": 189
}
```
**USER Request:**
```
{
    "email": "varlikesraa@email.com",
    "password":"password",
    "userType":"INDIVIDUAL",
    "phoneNumber":"8545345925",
    "role":"USER"

}
```
```http
POST http://localhost:8080/register
```
Response: 200 OK
```json
{
  "id": 7,
  "email": "varlikesraa@email.com",
  "role": "USER",
  "userType": "INDIVIDUAL",
  "phoneNumber": "8545345925"
}
```
Request:
``` json
{
    "email":"varlikesraa@email.com",
    "password":"password"
}
```
```http
POST http://localhost:8080/login
```
Response: 200 OK
```json
{
  "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2YXJsaWtlc3JhYUBlbWFpbC5jb20iLCJpc3MiOiJqb2pvZXNyYSIsImV4cCI6MTY3NTE4NTQ2Mn0.nWoPABpST-j0w0lO6ow1xq8gbVd3tqdFKksUJ7hEI7Q",
  "user": {
    "id": 7,
    "email": "varlikesraa@email.com",
    "role": "USER",
    "userType": null,
    "phoneNumber": null
  }
}
```
~~ADMIN ve USER rolü erişebilir.  
**Authorization Type:** Bearer Token  
USER Token Example: ```"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.
eyJzdWIiOiJ2YXJsaWtlc3JhYUBlbWFpbC5jb20iLCJpc3MiOiJqb2pvZXNyYSIsImV4cCI6MTY3NTE4NTQ2Mn0.
nWoPABpST-j0w0lO6ow1xq8gbVd3tqdFKksUJ7hEI7Q"```

Request:
```http
GET http://localhost:8080/users/email/varlikesraa@email.com
```
Response: 200 OK
```json
{
  "createdDate": "2023-01-31T19:55:34.2702",
  "updatedDate": "2023-01-31T19:55:34.2702",
  "id": 7,
  "email": "varlikesraa@email.com",
  "password": "$2a$10$Uotn2V8z5QX/dnLO74fBHOh/Xezo5TMZXlauO1AK9IZwKoWZ1B8Vy",
  "userType": "INDIVIDUAL",
  "phoneNumber": "8545345925",
  "role": "USER",
  "notificationType": "EMAIL",
  "detail": null
}
```



