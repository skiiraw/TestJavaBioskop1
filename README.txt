CINEMAX - PROGRAM BIOSKOP JAVA OOP + GUI SWING

PERSYARATAN
- Java JDK 8 atau lebih baru (disarankan JDK 17+)
- Tidak perlu install library tambahan

CARA MENJALANKAN
1. Buka folder yang berisi BioskopApp.java di VS Code / terminal.
2. Buka terminal dalam folder tersebut.
3. Compile: javac BioskopApp.java
4. Jalankan: java BioskopApp

FITUR
- Memilih film (genre, durasi, rating usia)
- Memilih studio dan jam tayang
- Melihat kursi tersedia/terisi dan memilih beberapa kursi
- Menghitung total pembayaran
- Mengisi nama pemesan dan memesan tiket
- Menampilkan e-ticket dengan kode pemesanan
- Kursi yang dipesan tidak bisa dipilih lagi untuk jadwal tersebut

PENERAPAN OOP
- Film: atribut judul, genre, durasi, rating
- Studio: atribut nama, tipe, dimensi kursi, harga
- JadwalTayang: relasi Film-Studio, jam tayang, manajemen kursi
- Pemesanan: nama pembeli, daftar kursi, total, e-ticket
- BioskopApp: GUI Java Swing
- Encapsulation: atribut private dan getter
- Abstraction: validasi kursi dalam kelas model
- Association/Composition: JadwalTayang mempunyai Film dan Studio
- Method overriding: toString() pada JadwalTayang

CATATAN
Data masih disimpan di memori aplikasi. Setelah ditutup, status kursi akan kembali ke data contoh. Belum menggunakan database atau pembayaran sungguhan.
