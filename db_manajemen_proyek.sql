-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 11, 2026 at 02:20 PM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_manajemen_proyek`
--

-- --------------------------------------------------------

--
-- Table structure for table `proyek`
--

CREATE TABLE `proyek` (
  `idProjek` int NOT NULL,
  `namaProjek` varchar(100) NOT NULL,
  `deadline` date NOT NULL,
  `jenisProjek` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `proyek`
--

INSERT INTO `proyek` (`idProjek`, `namaProjek`, `deadline`, `jenisProjek`) VALUES
(1, 'Sistem Informasi Inventaris', '2026-11-10', 'Internal'),
(2, 'Website Manajemen Karyawan', '2026-11-15', 'Internal'),
(3, 'Aplikasi Absensi Pegawai', '2026-11-20', 'Internal'),
(4, 'Sistem Informasi Penjualan', '2026-11-25', 'Klien'),
(5, 'Website Toko Online', '2026-12-01', 'Klien'),
(6, 'Aplikasi Pemesanan Makanan', '2026-12-05', 'Klien');

-- --------------------------------------------------------

--
-- Table structure for table `proyekinternal`
--

CREATE TABLE `proyekinternal` (
  `idProjek` int NOT NULL,
  `divisiPeminta` varchar(100) NOT NULL,
  `tujuanProjek` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `proyekinternal`
--

INSERT INTO `proyekinternal` (`idProjek`, `divisiPeminta`, `tujuanProjek`) VALUES
(1, 'Divisi Gudang', 'Mempermudah pencatatan inventaris perusahaan'),
(2, 'Divisi HRD', 'Mengelola data dan informasi karyawan'),
(3, 'Divisi Administrasi', 'Mempermudah pencatatan kehadiran pegawai');

-- --------------------------------------------------------

--
-- Table structure for table `proyekklien`
--

CREATE TABLE `proyekklien` (
  `idProjek` int NOT NULL,
  `namaKlien` varchar(100) NOT NULL,
  `jenisKebutuhan` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `proyekklien`
--

INSERT INTO `proyekklien` (`idProjek`, `namaKlien`, `jenisKebutuhan`) VALUES
(4, 'PT Maju Bersama', 'Sistem penjualan dan laporan transaksi'),
(5, 'CV Berkah Digital', 'Website untuk penjualan produk secara online'),
(6, 'Restoran Lezat', 'Aplikasi pemesanan makanan secara online');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `proyek`
--
ALTER TABLE `proyek`
  ADD PRIMARY KEY (`idProjek`);

--
-- Indexes for table `proyekinternal`
--
ALTER TABLE `proyekinternal`
  ADD PRIMARY KEY (`idProjek`);

--
-- Indexes for table `proyekklien`
--
ALTER TABLE `proyekklien`
  ADD PRIMARY KEY (`idProjek`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `proyek`
--
ALTER TABLE `proyek`
  MODIFY `idProjek` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `proyekinternal`
--
ALTER TABLE `proyekinternal`
  ADD CONSTRAINT `proyekinternal_ibfk_1` FOREIGN KEY (`idProjek`) REFERENCES `proyek` (`idProjek`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `proyekklien`
--
ALTER TABLE `proyekklien`
  ADD CONSTRAINT `proyekklien_ibfk_1` FOREIGN KEY (`idProjek`) REFERENCES `proyek` (`idProjek`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
