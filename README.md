# Yardım Masası (Help Desk) Uygulaması

## Proje Açıklaması
Bu proje, kurum içindeki destek taleplerinin dijital olarak kaydedilmesi, takibi ve yönetimi amacıyla geliştirilmiş Yardım Masası uygulamasıdır. Farklı kullanıcı rolleri (admin, destek personeli, kullanıcı) için ayrı arayüzler ve yetkiler sunar. Java Swing ile masaüstü uygulaması olarak geliştirilmiştir ve MySQL veritabanı kullanmaktadır.

## Özellikler
- Destek taleplerinin oluşturulması, listelenmesi ve filtrelenmesi
- Taleplerin detaylarının görüntülenmesi
- Rol bazlı kullanıcı yönetimi (admin, support, user)
- Talep durumu ve önceliğinin güncellenmesi
- Modern ve kullanıcı dostu arayüz

## Demo Video
Uygulamanın tanıtım videosunu buradan izleyebilirsiniz:  
[Demo Video Linki](https://drive.google.com/drive/folders/1bBR9gtGs6v75wX1kH03--iZmzm6dSAlF?usp=share_link)

---

## Gereksinimler
- Java Development Kit (JDK) 17 veya üzeri
- MySQL Server (veritabanı için)
- Apache Maven (projeyi derlemek ve çalıştırmak için)

---

## Kurulum ve Çalıştırma Talimatları

### 1. Projeyi Klonlayın
```bash
git clone https://github.com/sudenurarslan/HelpDesk.git
cd HelpDesk
```

### 2. Veritabanını Hazırlayın
- MySQL üzerinde `helpdesk` adlı bir veritabanı oluşturun
- Projede bulunan `helpdesk.sql` dosyasını kullanarak tabloları ve başlangıç verilerini içeri aktarın
- `src/main/java/com/helpdesk/database/DatabaseConnection.java` dosyasını açın ve veritabanı bağlantı bilgilerinizi (URL, kullanıcı adı, parola) kendi ortamınıza göre düzenleyin:

```java
private static final String URL = "jdbc:mysql://localhost:3306/helpdesk";
private static final String USER = "root";
private static final String PASSWORD = "your_password";
```

### 3. Projeyi Derleyin
```bash
mvn clean install
```

### 4. Uygulamayı Çalıştırın
```bash
mvn exec:java -Dexec.mainClass="com.helpdesk.Main"
```

---

