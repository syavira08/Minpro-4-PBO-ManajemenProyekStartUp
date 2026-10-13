# Minpro-4-PBO-ManajemenProyekStartUp

Program ini dibuat menggunakan Java untuk membantu sebuah startup mencatat dan mengelola proyek-proyeknya. Program ini bisa menyimpan, menampilkan, mengubah, dan menghapus data proyek atau biasa disebut operasi CRUD (Create, Read, Update, Delete). Data proyek tidak lagi disimpan sementara di memori, melainkan disimpan permanen di database MySQL menggunakan JDBC, dan sebagai nilai tambah program juga dapat berjalan menggunakan framework ORM (Hibernate/JPA).

Program ini mengelola dua jenis proyek yang berbeda, yaitu:
- Proyek Internal: proyek yang dikerjakan untuk kebutuhan perusahaan sendiri
- Proyek Klien: proyek yang dikerjakan untuk klien/perusahaan luar

Kedua jenis proyek ini memiliki data umum yang sama (ID, nama, deadline), tetapi juga memiliki data tambahan yang berbeda sesuai kebutuhannya masing-masing.

---

## 1. Struktur Package
Program ini menerapkan struktur MVC (Model View Controller), yaitu struktur yang memisahkan program menjadi tiga peran utama yaitu Data (Model), Tampilan (View), dan Pengatur Alur (Controller), ditambah package `main` sebagai titik masuk program dan package `config` untuk pengaturan koneksi database. Berikut struktur package pada program ini:

```
src/main
├── java
│   ├── config
│   │   ├── Konfigurasi.java
│   │   ├── Koneksi.java
│   │   ├── JpaUtil.java
│   │   └── TestKoneksi.java
│   ├── controller
│   │   ├── ProyekCrud.java
│   │   ├── ProyekController.java
│   │   └── Validasi.java
│   ├── main
│   │   └── MainApp.java
│   ├── model
│   │   ├── Proyek.java
│   │   ├── ProyekInternal.java
│   │   ├── ProyekKlien.java
│   │   ├── ProyekDAO.java
│   │   ├── ProyekDAOJdbc.java
│   │   └── ProyekDAOJpa.java
│   └── view
│       └── ProyekView.java
└── resources
    └── META-INF
        └── persistence.xml
```

Penjelasan penerapan MVC pada setiap package:

- **`model`**: Berisi kelas `Proyek` (abstract class), `ProyekInternal`, dan `ProyekKlien` yang menyimpan struktur data proyek beserta aturan validasinya, serta interface `ProyekDAO` beserta dua implementasinya, `ProyekDAOJdbc` dan `ProyekDAOJpa`, yang bertugas membaca dan menulis data proyek ke database. Package ini merepresentasikan bagian **Model** dalam MVC. Model tidak ada hubungannya dengan tampilan menu, tugasnya menjaga data tetap konsisten dan mengurus penyimpanannya ke database.

- **`view`**: Berisi kelas `ProyekView`, yang merepresentasikan bagian **View** dalam MVC. Tugasnya menampilkan menu ke layar dan menerima input dari pengguna. View tidak menyimpan data sendiri, ia hanya meneruskan input pengguna ke controller dan menampilkan hasil yang diberikan controller. Pengulangan validasi input dibungkus dalam method pembantu seperti `inputTeks()` dan `inputDeadline()` agar kode tidak berulang.

- **`controller`**: Berisi interface `ProyekCrud`, kelas `ProyekController`, dan kelas `Validasi`, yang merepresentasikan bagian **Controller** dalam MVC. `ProyekCrud` mendefinisikan daftar operasi CRUD, dan `ProyekController` mengimplementasikannya sebagai penghubung antara `view` dan `model`: ia menerima permintaan dari view, meneruskannya ke DAO di `model` untuk menyimpan, mencari, mengubah, atau menghapus data di database, lalu mengembalikan hasilnya (berupa `boolean` atau objek) tanpa mencetak apa pun ke layar. `Validasi` mendukung controller dengan memastikan setiap input pengguna sudah benar sebelum diproses lebih lanjut.

- **`config`**: Berisi pengaturan koneksi database. `Koneksi` mengatur koneksi JDBC ke MySQL, `JpaUtil` membuat `EntityManagerFactory` untuk mode ORM, `Konfigurasi` menentukan mode yang dipakai (JDBC atau ORM), dan `TestKoneksi` adalah kelas kecil untuk mengecek apakah koneksi ke database berhasil.

- **`main`**: Berisi kelas `MainApp` yang menjadi titik masuk program. Kelas ini hanya memanggil satu method, yaitu `ProyekView.mulai()`, untuk memulai siklus menu dari awal sampai program ditutup.

Dengan pembagian ini, setiap bagian program bisa dikembangkan atau diperbaiki secara terpisah. Misalnya, jika tampilan menu ingin diubah, cukup edit file di `view` tanpa perlu menyentuh logika penyimpanan data di `model` atau `controller`. Hal ini membuat program lebih rapi, mudah dibaca, dan mudah dikembangkan lebih lanjut di kemudian hari.

---

## 2. Alur Program

### Langkah 1: Program dimulai
> <img width="500" alt="image" src="https://github.com/user-attachments/assets/e1b7f560-e365-4c74-ab1b-7773e2674b2e" />
Tampilan awal program saat pertama kali dijalankan menampilkan banner SISTEM MANAJEMEN PROYEK STARTUP, kemudian muncul menu utama dengan 5 pilihan, yaitu Tambah Proyek, Tampilkan Semua Proyek, Update Proyek, Hapus Proyek, dan Keluar. Pengguna dapat memilih menu dengan memasukkan angka pada bagian "Pilih menu:".

### Langkah 2: Program menjalankan pilihan menu
Disini pengguna diarahkan ke menu sesuai dengan angka yang dimasukkan. Setiap menu memiliki fungsi yang berbeda untuk mengelola data proyek. Berikut adalah penjelasan dari setiap menu yang tersedia pada program:

- **Menu 1: Tambah Proyek**
  > <img width="550" alt="image" src="https://github.com/user-attachments/assets/625d7ed9-07d7-4885-88f4-391a4e93ad2d" />
  Pengguna pertama-tama memilih jenis proyek yaitu Internal atau Klien. Setelah itu, program meminta data satu per satu yang terdiri dari ID (berupa angka), nama, deadline, dan data khusus sesuai jenis proyeknya. Jika semua data valid, proyek baru dibuat dan disimpan ke database.

  Salah satu contoh validasi data adalah jika ID yang dimasukkan sudah digunakan (dicek langsung ke database), program memberi tahu pengguna lalu menampilkan kembali bagian `INFORMASI PROYEK` dan meminta ID baru sampai ID yang dimasukkan belum pernah dipakai:
  ><img width="400" alt="image" src="https://github.com/user-attachments/assets/d47fcad1-d24b-4416-a694-7c681ed82aa7" />

  Berikut tampilan semua proyek yang menunjukkan bahwa proyek berhasil ditambahkan.
  > <img width="400" alt="image" src="https://github.com/user-attachments/assets/cb3f7112-5ad6-44c3-be49-b52d61c2fbff" />

- **Menu 2: Tampilkan Semua Proyek**
  > <img width="500" alt="image" src="https://github.com/user-attachments/assets/ead5833d-52d0-4cd8-8f61-4109557e510f" />
  Program membaca seluruh data proyek dari database dan menampilkannya, lengkap dengan nomor urut dan jenis proyeknya.

- **Menu 3: Update Proyek**
  > <img width="500" alt="image" src="https://github.com/user-attachments/assets/df2a9116-8347-4bd5-bbca-9ab3414ac29f" />
  Pengguna memasukkan ID proyek yang ingin diubah lalu program akan mencari proyek tersebut di database. Setelah ditemukan, data lama ditampilkan terlebih dahulu sebagai pembanding, kemudian pengguna diminta memasukkan data baru. Program otomatis mendeteksi apakah proyek tersebut jenis Internal atau Klien, sehingga pertanyaan yang muncul menyesuaikan.

  Pada bagian bawah setelah data lama, pengguna cukup menekan Enter jika tidak ingin mengubah suatu data, sehingga data lama tetap dipertahankan. Khusus deadline, jika pengguna memasukkan nilai yang formatnya salah, program akan meminta input ulang sampai formatnya benar atau pengguna menekan Enter. Perubahan kemudian disimpan ke database.

  > <img width="400" alt="image" src="https://github.com/user-attachments/assets/5b5b44c4-b46b-450b-bbdb-de2018e3c806" />
  Gambar diatas merupakan tampilan semua proyek yang menunjukkan bahwa proyek berhasil diupdate.

- **Menu 4: Hapus Proyek**
  > <img width="400" alt="image" src="https://github.com/user-attachments/assets/4dc7ae3c-815e-4893-835e-34a79e28d0e4" />
  Pengguna diarahkan untuk memasukkan ID proyek yang ingin dihapus, lalu datanya langsung dihapus dari database dan program memberi konfirmasi.
  > <img width="500" alt="image" src="https://github.com/user-attachments/assets/e52b6e27-4ce0-44e6-b3ca-72eb79723f2b" />
  Gambar diatas merupakan tampilan semua proyek yang menunjukkan bahwa proyek berhasil dihapus.

- **Menu 5 – Keluar**
  > <img width="550" alt="image" src="https://github.com/user-attachments/assets/e04a5ae1-9523-4c89-bca1-bdebb3fd85dc" />
  Program akan menghentikan perulangan dan menampilkan pesan penutup, kemudian `Scanner` ditutup agar program berakhir dengan baik.

### Langkah 3: Kembali ke menu
> <img width="350" alt="image" src="https://github.com/user-attachments/assets/c229be53-2fe9-4750-8ac2-07dbee9ac59d" />
  Setelah satu aksi selesai, program otomatis kembali menampilkan menu utama. Siklus ini terus berulang sampai pengguna memilih menu "Keluar".

---

## 3. Penerapan Encapsulation dan Inheritance

### Encapsulation 
Encapsulation diterapkan dengan menyembunyikan data di dalam kelas dan hanya mengizinkan akses melalui method tertentu, bukan langsung dari luar.

Semua atribut pada kelas `Proyek` dideklarasikan sebagai `private`, sehingga tidak bisa diakses atau diubah langsung dari kelas lain:
><img width="500" alt="Screenshot 2026-09-24 221304" src="https://github.com/user-attachments/assets/986143df-72d7-4dce-8196-0ec1917bedef" />
Untuk membaca atau mengubah data tersebut, harus melalui method getter dan setter yang bersifat `public`. Setiap setter juga tidak langsung menyimpan data yang diberikan, melainkan memanggil kelas `Validasi` terlebih dahulu untuk memeriksa kevalidannya. Contohnya pada `setNamaProjek()`:
><img width="700" alt="Screenshot 2026-09-24 221612" src="https://github.com/user-attachments/assets/d3e3443f-ccc0-4ffa-b30c-3326f23dd7af" />

### Inheritance
Inheritance diterapkan pada relasi antar kelas model, di mana satu kelas menurunkan atribut dan method-nya ke kelas lain.

`Proyek` berperan sebagai **superclass** yang menyimpan hal-hal yang dimiliki semua jenis proyek: ID, nama, deadline, serta method umum `getJenisProjek()` dan `cetakData()`:
><img width="800" alt="image" src="https://github.com/user-attachments/assets/a47720ae-d512-4949-917b-0b9aca6497f9" />
`ProyekInternal` dan `ProyekKlien` adalah **subclass** yang meng-*extend* `Proyek`. Keduanya otomatis mendapatkan seluruh atribut dan method milik `Proyek`, lalu menambahkan atribut khas miliknya sendiri. Saat atribut subclass dibuat, konstruktornya memanggil `super(idProjek, namaProjek, deadline)` untuk mengisi data umum lewat kelas induk, sehingga kode tidak perlu ditulis dua kali:
><img width="600" alt="Screenshot 2026-09-24 221916" src="https://github.com/user-attachments/assets/a6d32303-6bdb-4e2d-b2a4-181377402518" />

><img width="600" alt="Screenshot 2026-09-24 222106" src="https://github.com/user-attachments/assets/b75654dd-6741-472e-b917-6135a340b29b" />
Dengan inheritance, penambahan jenis proyek baru menjadi lebih mudah, cukup dengan membuat subclass baru yang meng-*extend* `Proyek`.

## 4. Penerapan Polymorphism dan Abstraction

### a. Abstraction (Abstract Class dan Abstract Method)
Abstraction diterapkan dengan menyembunyikan detail yang berbeda-beda di tiap jenis proyek dan hanya menyisakan "kerangka" yang wajib dipenuhi. Pada program ini, `Proyek` dibuat sebagai **abstract class**, sehingga tidak bisa dibuat objeknya secara langsung (hanya `ProyekInternal` dan `ProyekKlien` yang bisa dibuat). Di dalamnya terdapat dua **abstract method**, yaitu method yang hanya punya deklarasi tanpa isi:
><img width="900" alt="image" src="https://github.com/user-attachments/assets/eb047322-fd92-4464-8179-743bc0bb136b" />
- `getJenisProjek()` wajib diisi oleh subclass untuk menyebutkan jenis proyeknya ("Internal" atau "Klien").
- `update(nama, deadline, data1, data2)` wajib diisi oleh subclass karena data khusus yang diubah berbeda: `ProyekInternal` mengubah divisi dan tujuan, sedangkan `ProyekKlien` mengubah nama klien dan jenis kebutuhan. `Proyek` sendiri tidak mengenal atribut-atribut tersebut.

### b. Polymorphism (Method Overriding)
Polymorphism artinya method dengan nama yang sama bisa memberikan hasil berbeda tergantung objek yang memanggilnya. Dalam program ini, polymorphism diterapkan lewat **method overriding** pada method `getJenisProjek()`, `cetakData()`, dan `update()` 4 parameter.

Method `cetakData()` didefinisikan secara umum di kelas induk `Proyek`, sedangkan `getJenisProjek()` dan `update()` 4 parameter masih berupa abstract (lihat bagian Abstraction). Ketiganya kemudian ditulis ulang atau di-*override* oleh `ProyekInternal`:
><img width="900" alt="image" src="https://github.com/user-attachments/assets/54a183ae-16d9-43bd-bd23-0f363d4c4374" />
dan juga oleh `ProyekKlien`, dengan isi yang berbeda sesuai kebutuhannya:
><img width="900" alt="image" src="https://github.com/user-attachments/assets/6286ccaa-3001-4e6b-8312-65b9b30ff6f1" />

Pada `cetakData()` di kedua subclass, program tetap memanggil `super.cetakData()` terlebih dahulu agar data umum tetap tercetak, lalu menambahkan baris cetak untuk atribut khususnya sendiri, sehingga kode tidak diulang percuma.

### c. Polymorphism (Method Overloading)
Selain overriding, polymorphism juga diterapkan lewat **method overloading**, yaitu beberapa method dengan nama yang sama tetapi jumlah parameter berbeda. Pada kelas `Proyek`, method `update()` memiliki dua versi:
><img width="900" alt="image" src="https://github.com/user-attachments/assets/14a8335c-665e-4dae-b092-cce888e65187" />
Kedua versi ini punya tujuan berbeda dan saling melengkapi. Versi 2 parameter berisi logika yang sama untuk semua jenis proyek (mengubah nama dan deadline), sehingga ditulis sekali di `Proyek`. Versi 4 parameter menangani data khusus tiap jenis proyek dan wajib diisi oleh subclass, yang di dalamnya memanggil versi 2 parameter (`update(namaBaru, deadlineBaru)`) agar kode pengubah nama dan deadline tidak ditulis ulang.

---

## 5. Penerapan JDBC

**JDBC (Java Database Connectivity)** adalah API standar Java untuk menghubungkan program dengan database. Pada program ini JDBC dipakai untuk menyimpan data proyek ke database **MySQL** bernama `db_manajemen_proyek`, menggunakan driver `mysql-connector-j` yang ditambahkan lewat `pom.xml`.

### a. Koneksi Database
Koneksi diatur oleh kelas `Koneksi` di package `config`:
><img width="700" alt="image" src="https://github.com/user-attachments/assets/1bb56fce-d1b0-4895-bc99-a09ec86d82b3" />

Koneksi disimpan dalam variabel `static` sehingga tidak perlu membuat koneksi baru untuk setiap operasi. Koneksi hanya dibuat ulang jika belum ada atau sudah terputus. Kelas `TestKoneksi` dapat dijalankan sendiri untuk mengecek apakah koneksi berhasil dan berapa data yang ada di tabel `proyek`.

Agar program dapat berjalan, MySQL harus aktif dan database `db_manajemen_proyek` harus sudah dibuat, dengan USER dan PASS pada `Koneksi` disesuaikan dengan pengaturan MySQL masing-masing.

### b. Struktur Tabel
Karena `Proyek` memiliki dua jenis subclass dengan atribut berbeda, data disimpan dalam tiga tabel yang saling berelasi:

| Tabel | Kolom | Keterangan |
|---|---|---|
| `proyek` | `idProjek`, `namaProjek`, `deadline`, `jenisProjek` | Data umum semua proyek, `idProjek` adalah primary key, `deadline` bertipe `DATE`, dan `jenisProjek` berisi "Internal" atau "Klien" |
| `proyekinternal` | `idProjek`, `divisiPeminta`, `tujuanProjek` | Data khusus Proyek Internal, `idProjek` adalah foreign key ke `proyek` |
| `proyekklien` | `idProjek`, `namaKlien`, `jenisKebutuhan` | Data khusus Proyek Klien, `idProjek` adalah foreign key ke `proyek` |

Kedua foreign key memakai `ON DELETE CASCADE`, sehingga ketika baris di tabel `proyek` dihapus, baris detailnya ikut terhapus otomatis.

### c. Pola DAO (Data Access Object) dan Interface
Semua perintah SQL dikumpulkan di satu tempat, yaitu **DAO**, agar controller tidak berisi SQL. Interface `ProyekDAO` mendefinisikan lima operasi yang harus tersedia, tanpa menjelaskan cara kerjanya:
><img width="500" alt="image" src="https://github.com/user-attachments/assets/25f844cd-67cd-4564-8447-1fe96e0d1a06" />
`ProyekDAOJdbc` mengimplementasikan interface tersebut menggunakan JDBC. `ProyekController` kemudian hanya memanggil method milik `ProyekDAO` dan tidak perlu tahu bagaimana data disimpan:
><img width="900" alt="image" src="https://github.com/user-attachments/assets/afad3ddf-7525-41f4-b007-7521db2d596c" />

Lapisan di atasnya juga memakai interface. Interface `ProyekCrud` di package `controller` berisi daftar operasi CRUD yang harus tersedia:
><img width="900" alt="image" src="https://github.com/user-attachments/assets/99c65e5d-cac7-4f3d-9123-637b52b42f73" />
`ProyekController` mengimplementasikan `ProyekCrud`, dan `ProyekView` memakai interface ini sebagai tipe variabelnya, bukan kelas `ProyekController` secara langsung:
><img width="700" alt="image" src="https://github.com/user-attachments/assets/b3b3962c-7cc8-4abc-a907-8fe8a479e54d" />
Dengan cara ini, View hanya bergantung pada `ProyekCrud`, dan controller hanya bergantung pada `ProyekDAO`. Jika cara penyimpanan diganti, cukup membuat kelas DAO baru tanpa mengubah `ProyekView`.

### d. Penerapan CRUD dengan JDBC
Seluruh query pada `ProyekDAOJdbc` memakai **`PreparedStatement`** dengan tanda `?` sebagai placeholder, sehingga nilai dari pengguna dikirim sebagai parameter dan tidak digabung langsung ke teks SQL (aman dari SQL Injection). `PreparedStatement` dan `ResultSet` dibuka dengan *try-with-resources* agar otomatis ditutup.

- **Create**: data umum dimasukkan ke tabel `proyek`, lalu data khususnya dimasukkan ke `proyekinternal` atau `proyekklien` sesuai jenis objeknya. Kedua `INSERT` dijalankan dalam satu **transaksi** (`setAutoCommit(false)`). Jika keduanya berhasil dilakukan `commit()`, jika salah satu gagal dilakukan `rollback()` sehingga data tidak tersimpan setengah-setengah.
  ><img width="700" alt="image" src="https://github.com/user-attachments/assets/80bed022-8426-45cc-a5c7-b6760d7ecc16" />

- **Read**: data diambil dengan satu query yang menggabungkan ketiga tabel memakai `LEFT JOIN`, lalu setiap baris hasilnya diubah menjadi objek `ProyekInternal` atau `ProyekKlien` oleh method `petakanBaris()`.
  ><img width="700" alt="image" src="https://github.com/user-attachments/assets/f30b87a9-430b-48d1-ba63-e6e1099d3417" />

  Method `getAllProyek()` menambahkan `ORDER BY p.idProjek`, sedangkan `cariProyekById()` menambahkan `WHERE p.idProjek = ?`.
- **Update**: tabel `proyek` diperbarui (`namaProjek` dan `deadline`), lalu tabel detail sesuai jenis proyek diperbarui. Seperti pada Create, keduanya berada dalam satu transaksi.
  ><img width="600" alt="image" src="https://github.com/user-attachments/assets/30542cf0-dcc2-449f-9c6a-2bca0f97fc8f" />

- **Delete**: cukup menjalankan `DELETE FROM proyek WHERE idProjek=?`, karena baris di tabel detail ikut terhapus lewat `ON DELETE CASCADE`.
  ><img width="600" alt="image" src="https://github.com/user-attachments/assets/3742ca96-7667-4898-8833-d6a146f2dd0e" />

Kolom `deadline` bertipe `DATE`, sedangkan di program deadline dimasukkan dengan format `dd-mm-yyyy`. Konversi dua arah dilakukan oleh `Validasi.parseTanggal()` (teks menjadi `LocalDate`) dan `Validasi.formatTanggal()` (`LocalDate` menjadi teks).

---

## 6. Penerapan Nilai Tambah: Framework ORM

Nilai tambah pada program ini adalah penerapan **ORM (Object Relational Mapping)** menggunakan **Hibernate** yang mengikuti standar **JPA (Jakarta Persistence)**. ORM memetakan kelas Java langsung ke tabel database, sehingga operasi CRUD dapat dilakukan lewat objek tanpa menulis SQL manual seperti pada JDBC. Dependency-nya (`hibernate-core`) ditambahkan di `pom.xml`.

### a. Pemetaan Kelas Model ke Tabel (Entity)
Kelas model diberi anotasi JPA agar dikenali sebagai tabel:

| Anotasi | Letak | Fungsi |
|---|---|---|
| `@Entity`, `@Table(name = ...)` | `Proyek`, `ProyekInternal`, `ProyekKlien` | Menandai kelas sebagai entity dan menentukan nama tabelnya |
| `@Id`, `@Column(...)` | atribut di setiap kelas | Menandai primary key dan memetakan atribut ke kolom (termasuk `nullable` dan `length`) |
| `@Inheritance(strategy = InheritanceType.JOINED)` | `Proyek` | Inheritance dipetakan ke tabel terpisah: data umum di `proyek`, data khusus di tabel subclass |
| `@DiscriminatorColumn` dan `@DiscriminatorValue` | `Proyek`, subclass | Kolom `jenisProjek` menentukan apakah satu baris menjadi `ProyekInternal` ("Internal") atau `ProyekKlien` ("Klien") |
| `@PrimaryKeyJoinColumn(name = "idProjek")` | `ProyekInternal`, `ProyekKlien` | Menghubungkan tabel subclass ke tabel `proyek` lewat `idProjek` |

Contoh pada kelas `Proyek`:
><img width="900" alt="image" src="https://github.com/user-attachments/assets/e94369df-7614-45ec-802b-74d3e20bd348" />

Dengan begitu struktur inheritance `Proyek`, `ProyekInternal`, dan `ProyekKlien` yang sudah ada langsung dipakai ORM tanpa perlu dibuat ulang. Tabel yang dipakai sama dengan tabel pada JDBC (lihat bagian 5b).

### b. Konfigurasi ORM
- **`persistence.xml`** (di `src/main/resources/META-INF`) berisi persistence unit bernama `manajemenProyekPU`, yang mendaftarkan ketiga entity beserta driver, URL, user, dan password database.
- **`JpaUtil`** (package `config`) membuat `EntityManagerFactory` dari persistence unit tersebut. Objek ini dibuat sekali lalu dipakai ulang oleh semua operasi.
  ><img width="800" alt="image" src="https://github.com/user-attachments/assets/801cb9a9-2038-41d0-a2fd-188c66835074" />


### c. Penerapan CRUD dengan ORM
CRUD dengan ORM ditulis di `ProyekDAOJpa`, yang mengimplementasikan interface `ProyekDAO` yang sama dengan `ProyekDAOJdbc`. Semua operasi dilakukan lewat `EntityManager`, dan operasi yang mengubah data dibungkus transaksi (`begin`, `commit`, dan `rollback` jika gagal):

| Operasi | Method JPA | Keterangan |
|---|---|---|
| Create | <img width="1000" alt="image" src="https://github.com/user-attachments/assets/b021eb34-a6c1-4c69-a24b-56d136070e2c" /> | Hibernate otomatis membuat `INSERT` ke tabel `proyek` dan tabel subclass |
| Read (semua) | <img width="1200" alt="image" src="https://github.com/user-attachments/assets/db5054bb-690e-4983-8e81-57efa1fe9453" /> | Query **JPQL** yang bekerja pada kelas `Proyek`, bukan pada tabel. Hasilnya otomatis berupa campuran `ProyekInternal` dan `ProyekKlien` |
| Read (by id) | <img width="1100" alt="image" src="https://github.com/user-attachments/assets/784169f4-bad4-4acb-b08b-804bd172f3af" /> | Mengembalikan `null` jika data tidak ada |
| Update | <img width="1000" alt="image" src="https://github.com/user-attachments/assets/c3d6a432-34a5-48ab-8606-93cc4fe21fde" /> | Sebelum `merge`, program memastikan data memang ada lewat `em.find()`, karena `merge()` akan melakukan `INSERT` jika ID belum ada |
| Delete | <img width="1000" alt="image" src="https://github.com/user-attachments/assets/80aca7c5-e4be-4f50-a9eb-1faf4cdccb04" /> | Data dicari dulu dengan `em.find()`, lalu dihapus |

Dibandingkan JDBC, tidak ada lagi `PreparedStatement`, `ResultSet`, ataupun `JOIN` manual karena semuanya diurus Hibernate.

### d. Memilih Mode JDBC atau ORM
Pemilihan mode dilakukan di satu tempat, yaitu konstanta pada kelas `Konfigurasi`:
><img width="500" alt="image" src="https://github.com/user-attachments/assets/90361916-033c-4d72-a449-a7a115a657cc" />

`ProyekController` memilih DAO berdasarkan konstanta tersebut:
><img width="900" alt="image" src="https://github.com/user-attachments/assets/886b2eea-46b0-47f6-80f4-8b4bb3f937b5" />

Karena `ProyekDAOJdbc` dan `ProyekDAOJpa` sama-sama mengimplementasikan `ProyekDAO`, perpindahan antara JDBC dan ORM cukup dilakukan dengan mengganti nilai `GUNAKAN_ORM`, tanpa mengubah satu baris pun di `ProyekController`, `ProyekView`, maupun kelas model. Tampilan dan alur program tetap sama persis (seperti pada bagian 2) karena hanya cara penyimpanan datanya yang berbeda.
