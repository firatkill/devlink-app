# 🚀 DevLink: Geliştiriciler İçin Profesyonel Portföy Platformu

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-Latest-green.svg)](https://spring.io/projects/spring-boot)
[![Jakarta EE](https://img.shields.io/badge/Jakarta%20EE-Latest-blue.svg)](https://jakarta.ee/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Latest-blue.svg)](https://www.postgresql.org/)
[![JWT](https://img.shields.io/badge/JWT-Authentication-yellow.svg)](https://jwt.io/)

## 📋 Genel Bakış

DevLink, yazılım geliştiricilerin ve teknik profesyonellerin kişisel profillerini, projelerini ve portföylerini sergileyebilecekleri özelleştirilmiş bir platformdur. Geliştiriciler DevLink üzerinde profesyonel dijital kimliklerini oluşturabilir, sosyal medya ve dış bağlantılarını entegre edebilir, öne çıkan projelerini gösterebilir ve hedef kitlelerine kendilerini etkili bir şekilde tanıtabilirler.

Teknoloji alanında kendinizi göstermenin ve profesyonel ağınızı genişletmenin en etkili yolu artık DevLink!

### ✨ Temel Özellikler

- **📝 Özelleştirilebilir Profiller**: Kişisel görünüm, başlık ve açıklamalarla benzersiz bir profil oluşturun
- **🖼️ Proje Vitrini**: En iyi projelerinizi resimler, açıklamalar ve teknoloji bilgileriyle sergileyin
- **🔗 Sosyal Medya Entegrasyonu**: GitHub, LinkedIn, Twitter gibi hesaplarınızı profilinize bağlayın
- **🌐 Özel Bağlantı Yönetimi**: Portföy, blog veya diğer kişisel sitelerinize özel ikonlarla bağlantılar ekleyin
- **👁️ Görünürlük Kontrolü**: Profilinizin veya belirli öğelerin görünürlüğünü tam olarak kontrol edin
- **📊 Görüntülenme İstatistikleri**: Profilinizin kaç kez görüntülendiğini takip edin
- **🔒 Güvenli Kimlik Doğrulama**: JWT tabanlı güvenli kimlik doğrulama sistemi

## 💻 API Dokümantasyonu

DevLink, kullanıcı profilleri, projeler ve kimlik doğrulama için kapsamlı bir RESTful API sunar. Aşağıda her endpoint için ayrıntılı dokümantasyon bulunmaktadır.

### 🌐 Temel URL

```
http://localhost:8080
```

### 🔐 Kimlik Doğrulama (Authentication)

#### Yeni Kullanıcı Kaydı

```
POST /api/auth/register
```

**İstek (Request) Body:**

```json
{
  "username": "johndoe",
  "email": "john.doe@example.com",
  "password": "securepassword"
}
```

**Gereksinimler:**
- Kullanıcı adı, e-posta ve şifre zorunludur
- Kullanıcı adı 3-24 karakter arasında olmalıdır
- Şifre en az 4 karakter olmalıdır

**Yanıt (Response):**

```json
{
  "success": true,
  "message": "User registered successfully",
  "error": null,
  "data": null
}
```

#### Giriş Yapma

```
POST /api/auth/login
```

**İstek (Request) Body (Kullanıcı adı ile):**

```json
{
  "username": "johndoe",
  "password": "securepassword"
}
```

**VEYA (E-posta ile):**

```json
{
  "email": "john.doe@example.com",
  "password": "securepassword"
}
```

**Gereksinimler:**
- Kullanıcı adı veya e-postadan en az biri sağlanmalıdır
- Şifre zorunludur

**Yanıt (Response):**

```json
{
  "success": true,
  "message": "Login successful",
  "error": null,
  "data": {
    "username": "johndoe",
    "email": "john.doe@example.com",
    "token": "jwt_token_here"
  }
}
```

### 👤 Kullanıcı Yönetimi

#### Kullanıcı Bilgilerini Güncelleme

```
PUT /api/user
```

**İstek (Request) Body:**

```json
{
  "username": "johndoe_updated",
  "email": "john.updated@example.com",
  "password": "newsecurepassword"
}
```

**Gereksinimler:**
- Kullanıcı adı, e-posta ve şifre zorunludur
- Kullanıcı adı 3-24 karakter arasında olmalıdır
- Şifre en az 4 karakter olmalıdır

**Yanıt (Response):**

```json
{
  "success": true,
  "message": "User updated successfully",
  "error": null,
  "data": {
    "username": "johndoe_updated",
    "email": "john.updated@example.com",
    "token": "updated_jwt_token_here"
  }
}
```

#### Kullanıcı Hesabını Silme

```
DELETE /api/user
```

**Yanıt (Response):**

```json
{
  "success": true,
  "message": "User deleted successfully",
  "error": null,
  "data": null
}
```

### 🖼️ Profil Yönetimi

#### Profil Resmini Güncelleme

```
PUT /api/profile/update-image
```

**İstek (Request) Body:**

```json
{
  "image": "base64_encoded_image_string"
}
```

**Gereksinimler:**
- Resim zorunludur (base64 kodlanmış string olarak)

**Yanıt (Response):**

```json
{
  "success": true,
  "message": "Profile image updated successfully",
  "error": null,
  "data": {
    "image": "base64_encoded_image_string"
  }
}
```

#### Profil Başlık Bilgilerini Güncelleme

```
PUT /api/profile/update-header
```

**İstek (Request) Body:**

```json
{
  "displayName": "John Doe",
  "headerTitle": "Senior Software Developer",
  "headerDescription": "Full-stack developer with 8 years of experience in web technologies."
}
```

**Yanıt (Response):**

```json
{
  "success": true,
  "message": "Profile header updated successfully",
  "error": null,
  "data": {
    "displayName": "John Doe",
    "headerTitle": "Senior Software Developer",
    "headerDescription": "Full-stack developer with 8 years of experience in web technologies."
  }
}
```

#### Sosyal Medya Bağlantılarını Güncelleme

```
PUT /api/profile/social-links
```

**İstek (Request) Body:**

```json
[
  {
    "platform": "GITHUB",
    "url": "https://github.com/johndoe",
    "hidden": false
  },
  {
    "platform": "LINKEDIN",
    "url": "https://linkedin.com/in/johndoe",
    "hidden": false
  },
  {
    "platform": "TWITTER",
    "url": "https://twitter.com/johndoe",
    "hidden": true
  }
]
```

**Desteklenen Platformlar:**
- GITHUB
- LINKEDIN
- FACEBOOK
- TWITTER
- INSTAGRAM

**Yanıt (Response):**

```json
{
  "success": true,
  "message": "Social links updated successfully",
  "error": null,
  "data": [
    {
      "id": 1,
      "platform": "GITHUB",
      "url": "https://github.com/johndoe",
      "hidden": false
    },
    {
      "id": 2,
      "platform": "LINKEDIN",
      "url": "https://linkedin.com/in/johndoe",
      "hidden": false
    },
    {
      "id": 3,
      "platform": "TWITTER",
      "url": "https://twitter.com/johndoe",
      "hidden": true
    }
  ]
}
```

#### Dış Bağlantıları Güncelleme

```
PUT /api/profile/external-links
```

**İstek (Request) Body:**

```json
[
  {
    "icon": "base64_encoded_icon_string",
    "title": "Kişisel Web Sitem",
    "url": "https://johndoe.com",
    "hidden": false
  },
  {
    "icon": "base64_encoded_icon_string",
    "title": "Teknik Blogum",
    "url": "https://blog.johndoe.com",
    "hidden": false
  }
]
```

**Yanıt (Response):**

```json
{
  "success": true,
  "message": "External links updated successfully",
  "error": null,
  "data": [
    {
      "id": 1,
      "icon": "base64_encoded_icon_string",
      "title": "Kişisel Web Sitem",
      "url": "https://johndoe.com",
      "hidden": false
    },
    {
      "id": 2,
      "icon": "base64_encoded_icon_string",
      "title": "Teknik Blogum",
      "url": "https://blog.johndoe.com",
      "hidden": false
    }
  ]
}
```

#### Profil Görünürlüğünü Yönetme

**Profili Görünür Yapma:**

```
PUT /api/profile/show-profile
```

**Yanıt (Response):**

```json
{
  "success": true,
  "message": "Profile visibility set to visible",
  "error": null,
  "data": null
}
```

**Profili Gizleme:**

```
PUT /api/profile/hide-profile
```

**Yanıt (Response):**

```json
{
  "success": true,
  "message": "Profile visibility set to hidden",
  "error": null,
  "data": null
}
```

### 📂 Proje Yönetimi

#### Yeni Proje Oluşturma

```
POST /api/profile/projects
```

**İstek (Request) Body:**

```json
{
  "image": "base64_encoded_image_string",
  "name": "E-ticaret Platformu",
  "description": "Admin paneli ve ödeme entegrasyonu içeren tam kapsamlı bir e-ticaret çözümü.",
  "deploymentUrl": "https://myproject.com",
  "sourceCodeUrl": "https://github.com/johndoe/ecommerce",
  "techsUsed": ["React", "Node.js", "MongoDB", "Express"],
  "hidden": false
}
```

**Yanıt (Response):**

```json
{
  "success": true,
  "message": "Project created successfully",
  "error": null,
  "data": {
    "id": 1,
    "image": "base64_encoded_image_string",
    "name": "E-ticaret Platformu",
    "description": "Admin paneli ve ödeme entegrasyonu içeren tam kapsamlı bir e-ticaret çözümü.",
    "deploymentUrl": "https://myproject.com",
    "sourceCodeUrl": "https://github.com/johndoe/ecommerce",
    "techsUsed": ["React", "Node.js", "MongoDB", "Express"],
    "hidden": false
  }
}
```

#### Mevcut Projeyi Güncelleme

```
PUT /api/profile/projects
```

**İstek (Request) Body:**

```json
{
  "id": 1,
  "image": "updated_base64_encoded_image_string",
  "name": "E-ticaret Platformu v2",
  "description": "Yeni özelliklerle güncellenen tanım ve gelişmiş kullanıcı deneyimi.",
  "deploymentUrl": "https://myproject-v2.com",
  "sourceCodeUrl": "https://github.com/johndoe/ecommerce-v2",
  "techsUsed": ["React", "Node.js", "MongoDB", "Express", "Redis"],
  "hidden": false
}
```

**Gereksinimler:**
- Proje ID'si zorunludur

**Yanıt (Response):**

```json
{
  "success": true,
  "message": "Project updated successfully",
  "error": null,
  "data": {
    "id": 1,
    "image": "updated_base64_encoded_image_string",
    "name": "E-ticaret Platformu v2",
    "description": "Yeni özelliklerle güncellenen tanım ve gelişmiş kullanıcı deneyimi.",
    "deploymentUrl": "https://myproject-v2.com",
    "sourceCodeUrl": "https://github.com/johndoe/ecommerce-v2",
    "techsUsed": ["React", "Node.js", "MongoDB", "Express", "Redis"],
    "hidden": false
  }
}
```

#### Proje Silme

```
DELETE /api/profile/projects/{projectId}
```

**Path Parametreleri:**
- projectId: Silinecek projenin ID'si

**Yanıt (Response):**

```json
{
  "success": true,
  "message": "Project deleted successfully",
  "error": null,
  "data": null
}
```

### 🌍 Halka Açık Endpointler

#### Kullanıcı Profili Görüntüleme

```
GET /api/public/{username}
```

**Path Parametreleri:**
- username: Profilini görüntülemek istediğiniz geliştiricinin kullanıcı adı

**Yanıt (Response):**

```json
{
  "success": true,
  "message": "Profile retrieved successfully",
  "error": null,
  "data": {
    "id": 1,
    "user": {
      "username": "johndoe"
    },
    "displayName": "John Doe",
    "headerTitle": "Senior Software Developer",
    "headerDescription": "Web teknolojilerinde 8 yıllık deneyime sahip full-stack geliştirici.",
    "image": "base64_encoded_image_string",
    "socialLinks": [
      {
        "id": 1,
        "url": "https://github.com/johndoe",
        "platform": "GITHUB",
        "hidden": false
      },
      {
        "id": 2,
        "url": "https://linkedin.com/in/johndoe",
        "platform": "LINKEDIN",
        "hidden": false
      }
    ],
    "externalLinks": [
      {
        "id": 1,
        "icon": "base64_encoded_icon_string",
        "title": "Kişisel Web Sitem",
        "url": "https://johndoe.com",
        "hidden": false
      }
    ],
    "projects": [
      {
        "id": 1,
        "image": "base64_encoded_image_string",
        "name": "E-ticaret Platformu",
        "description": "Admin paneli ve ödeme entegrasyonu içeren tam kapsamlı bir e-ticaret çözümü.",
        "deploymentUrl": "https://myproject.com",
        "sourceCodeUrl": "https://github.com/johndoe/ecommerce",
        "techsUsed": ["React", "Node.js", "MongoDB", "Express"],
        "hidden": false
      }
    ],
    "updatedAt": "2025-06-28T10:15:30.123Z",
    "hidden": false
  }
}
```

#### Profil Görüntüleme Sayısını Artırma

```
PATCH /api/public/increment-views/{profileid}
```

**Path Parametreleri:**
- profileid: Görüntüleme sayısını artırmak istediğiniz profilin ID'si

**Yanıt (Response):**

```json
{
  "success": true,
  "message": "View count incremented successfully",
  "error": null,
  "data": null
}
```

### 🧪 Test Endpointi

```
GET /dummy
```

**Yanıt (Response):**

Basit bir string döner, bağlantı testi için kullanılabilir.

## 🔧 Projeyi Kurma

### Ön Gereksinimler

- Java 21
- PostgreSQL
- Maven veya Gradle (derleme için)

### Konfigürasyon

Uygulama `application.properties` dosyası kullanılarak yapılandırılır. Önemli konfigürasyonlar şunlardır:

```properties
spring.application.name=DevLink

# Veritabanı Konfigürasyonu
spring.datasource.url=jdbc:postgresql://localhost:5432/devlink
spring.datasource.username=kullanıcıadı
spring.datasource.password=şifre

# JPA Konfigürasyonu
spring.jpa.hibernate.ddl-auto=update

# JWT Konfigürasyonu
app.jwt.expiration=86400000
app.jwt.secret_key=gizli_anahtarınız

# Swagger Dokümantasyonu
springdoc.api-docs.enabled=true
springdoc.swagger-ui.enabled=true
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/api-docs
springdoc.enable-spring-security=true
```

### Uygulamayı Çalıştırma

1. Repository'yi klonlayın
2. Veritabanı ayarlarınızı `application.properties` dosyasında yapılandırın
3. Projeyi derleyin
4. Uygulamayı çalıştırın

```bash
./mvnw spring-boot:run
```

API http://localhost:8080 adresinde ve Swagger UI dokümantasyonu http://localhost:8080/swagger-ui.html adresinde erişilebilir olacaktır.

## 🔒 Güvenlik

Uygulama, kimlik doğrulama için JWT (JSON Web Token) kullanır. Kullanıcı giriş yaptığında, sonraki istekler için Authorization başlığına dahil edilmesi gereken bir token alır:

```
Authorization: Bearer <token>
```

Tokenin varsayılan olarak 24 saatlik (86400000 milisaniye) bir geçerlilik süresi vardır.

## 🌟 API Kullanım Tavsiyeleri

1. **Kimlik Doğrulama**: Her zaman tokeni'nizi güvende tutun ve korumalı endpointlere yapılan isteklere dahil edin
2. **Base64 Kodlama**: Resimler ve simgeler için, düzgün şekilde Base64 ile kodlandıklarından emin olun
3. **Proje Vitrini**: En iyi sunum için, projelerinizde yüksek kaliteli görseller ve detaylı açıklamalar kullanın
4. **Profil Görünürlüğü**: Profilinizin başkalarına ne zaman görünür olacağını kontrol etmek için profili göster/gizle endpointlerini kullanın
5. **Optimize Edilmiş Görseller**: Performans için resimleri optimize edin ve uygun boyutlarda kullanın

## ⚠️ Hata İşleme

API tutarlı bir hata yanıt formatı kullanır:

```json
{
  "success": false,
  "message": "Hata açıklaması",
  "error": "Hata türü veya kodu",
  "data": null
}
```
