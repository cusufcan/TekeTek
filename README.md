# TekeTek

<img src="https://github.com/user-attachments/assets/a3a7a3d7-684c-43aa-a06e-c33e146ef316" alt="TekeTekLogo" width="128"/>

**TekeTek**, bir Android münazara uygulamasıdır. Kullanıcılara yapay zekâ destekli karşılıklı tartışma deneyimi sunar.

BTK AI Hackathon 2025 için geliştirilmiştir.

Kullanıcılar belirlenen konular üzerinde AI botu ile birebir münazaraya katılabilir, tartışmanın özetini görebilir ve kendi girmiş olduğu konular ile istediği kadar münazara yapıp kendi eksik ve güçlü yanlarını görebilir.

---

## Özellikler

- **Yapay zekâ ile münazara** — TekeTek Gemini AI botu ile belirlenen konu üzerinde tartışma yapabilirsiniz.
- **Canlı konu ekleme** — Kullanıcılar kendi eklediği münazara konuları ile istediği herhangi bir konuda Gemini AI ile münazara edebilir.
- **Anlık sohbet deneyimi** — Mesaj tabanlı, hızlı ve kesintisiz münazara akışı.
- **Tartışma özeti** — Münazara bitiminde güçlü/eksik noktalarınızı gösteren özet.
- **Koyu ve açık tema desteği** — Kullanıcı tercihine göre otomatik tema geçişi.

---

## Ekran Görüntüleri

![TekeTekLight](https://github.com/user-attachments/assets/0871c82c-6167-4dfd-bc29-50e838f689d3)

![TekeTekDark](https://github.com/user-attachments/assets/8368376a-14f9-43be-8bc2-e3c8b2380b25)

---

## Kullanılan Kütüphaneler

- **Ksp**: Kotlin Symbol Processing ile compile-time annotation işlemleri.  
- **Kotlin Serialization**: JSON veri dönüşümleri için hızlı ve güvenli çözüm.  
- **Kotlin Coroutines**: Asenkron işlemler ve akış yönetimi.  
- **Kotlin Parcelize**: Model nesnelerinin kolayca parcelable yapılmasını sağlar.  
- **Firebase Firestore**: Gerçek zamanlı veritabanı ve senkronizasyon.  
- **Jetpack Navigation**: Fragment geçişlerini yönetir.  
- **Navigation Safeargs**: Type-safe argüman geçişleri sağlar.  
- **Lifecycle (MVVM)**: ViewModel, LiveData ve lifecycle-aware yapılar.  
- **Hilt**: Dependency Injection yönetimi.  
- **Retrofit**: API istekleri için HTTP istemcisi.  
- **Lottie**: Animasyon gösterimi.  
- **Glide**: Görsel yükleme ve önbellekleme.  

---

## Proje Yapısı

```
/app
  /data
    /model
      /debate_end
      /debate_next
      /debate_start
    /remote
    /repository
  /di
  /domain
    /model
    /repository
    /usecase
      /debate
      /topic
  /ui
    /adapter
      /chat
      /summary
      /topic
    /event
    /main
    /presentation
      /add_topic
      /alert
      /debate
      /home
      /summary
      /topic
    /viewmodel
  /util
```

---

## Lisans

Bu proje [MIT Lisansı](LICENSE) ile lisanslanmıştır. Daha fazla bilgi için `LICENSE` dosyasına bakabilirsiniz.

---

## İletişim

Herhangi bir soru veya öneriniz varsa, benimle iletişime geçebilirsiniz:
 
- LinkedIn: [linkedin.com/in/cusufcan](https://linkedin.com/in/cusufcan)
- Email: [yusufcanmercan.info@gmail.com](mailto:yusufcanmercan.info@gmail.com)
