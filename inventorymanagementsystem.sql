-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 04, 2023 at 05:08 PM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `inventorymanagementsystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `catagory`
--

CREATE TABLE `catagory` (
  `catagoryId` varchar(255) NOT NULL,
  `catagoryName` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `catagory`
--

INSERT INTO `catagory` (`catagoryId`, `catagoryName`) VALUES
('Cat-0000001', 'Facewash'),
('Cat-0000003', 'clenser'),
('Cat-0000004', 'Scrub'),
('Cat-0000005', 'dust');

-- --------------------------------------------------------

--
-- Table structure for table `company`
--

CREATE TABLE `company` (
  `companyId` varchar(255) NOT NULL,
  `companyName` varchar(255) NOT NULL,
  `supName` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `company`
--

INSERT INTO `company` (`companyId`, `companyName`, `supName`) VALUES
('Sup-0000001', 'Natures Secrets', 'Edith Enterprice'),
('Sup-0000002', 'Janet', 'Kandana Distribute'),
('Sup-0000003', 'Amrin', 'Leaves Lanka'),
('Sup-0000004', '4 Ever', 'Karunanayake Distributes'),
('Sup-0000005', 'lady Bird', 'paneesha');

-- --------------------------------------------------------

--
-- Table structure for table `cusfewdetails`
--

CREATE TABLE `cusfewdetails` (
  `cusId` varchar(255) NOT NULL,
  `cusNic` varchar(255) NOT NULL,
  `cusName` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cusfewdetails`
--

INSERT INTO `cusfewdetails` (`cusId`, `cusNic`, `cusName`) VALUES
('C-0000000', '', 'Day to Day');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `cusId` varchar(255) NOT NULL,
  `cusFName` varchar(255) NOT NULL,
  `cusLName` varchar(255) NOT NULL,
  `cusNic` varchar(255) NOT NULL,
  `cusGender` varchar(255) NOT NULL,
  `cusDob` varchar(255) NOT NULL,
  `cusContactNo` varchar(255) NOT NULL,
  `cusEmail` varchar(255) NOT NULL,
  `cusAddress` varchar(255) NOT NULL,
  `cusNote` varchar(255) NOT NULL,
  `cusPerchesed` varchar(255) DEFAULT '00.00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`cusId`, `cusFName`, `cusLName`, `cusNic`, `cusGender`, `cusDob`, `cusContactNo`, `cusEmail`, `cusAddress`, `cusNote`, `cusPerchesed`) VALUES
('C-0000000', 'Customer', 'Day to Day', '', 'Male', '', '', '', '', 'This is shoert note about that customer', '1035.0');

-- --------------------------------------------------------

--
-- Table structure for table `expiredate`
--

CREATE TABLE `expiredate` (
  `no` varchar(255) NOT NULL,
  `grnNo` varchar(255) NOT NULL,
  `itemId` varchar(255) NOT NULL,
  `mfg` varchar(255) NOT NULL,
  `exp` varchar(255) NOT NULL,
  `batchNo` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `expiredate`
--

INSERT INTO `expiredate` (`no`, `grnNo`, `itemId`, `mfg`, `exp`, `batchNo`) VALUES
('0000000000000001', '145', 'Pdct-0000001', 'Jul 6, 2023', 'Jul 14, 2023', '523'),
('0000000000000002', '562', 'Pdct-0000001', 'Jul 6, 2023', 'Jul 29, 2023', '12'),
('0000000000000003', '122365489657', 'Pdct-0000012', 'Jul 7, 2023', 'Jul 1, 2023', '2365'),
('0000000000000004', '122365489657', 'Pdct-0000012', 'Jul 7, 2023', 'Jul 1, 2023', '2365');

-- --------------------------------------------------------

--
-- Table structure for table `grn`
--

CREATE TABLE `grn` (
  `grnNo` varchar(255) NOT NULL,
  `companyName` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  `date` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `grn`
--

INSERT INTO `grn` (`grnNo`, `companyName`, `value`, `date`) VALUES
('000000', 'Natures Secrets', '47736.0', '2023-Jun-29'),
('122365489657', '4 Ever', '47736.0', '2023-Jul-04'),
('124', 'Natures Secrets', '47736.0', '2023-Jul-04'),
('1254', 'Natures Secrets', '47736.0', '2023-Jul-04'),
('13456789', 'Natures Secrets', '47736.0', '2023-Jul-04'),
('145', 'Natures Secrets', '47736.0', '2023-Jul-04'),
('2365265', 'Natures Secrets', '47736.0', '2023-Jul-04'),
('2546951', 'Natures Secrets', '47736.0', '2023-Jul-04'),
('25864', 'Natures Secrets', '47736.0', '2023-Jul-04'),
('45874', 'Natures Secrets', '47736.0', '2023-Jul-04'),
('55', 'Natures Secrets', '47736.0', '2023-Jul-04'),
('562', 'Natures Secrets', '47736.0', '2023-Jul-04'),
('569', '4 Ever', '47736.0', '2023-Jul-02'),
('698569856', 'Natures Secrets', '47736.0', '2023-Jul-04'),
('756513167', 'Natures Secrets', '47736.0', '2023-Jun-27'),
('766570486', 'Natures Secrets', '47736.0', '2023-Jun-27'),
('774411765', 'Natures Secrets', '47736.0', '2023-Jun-27'),
('7777', 'Natures Secrets', '47736.0', '2023-Jul-02'),
('8888', 'Natures Secrets', '47736.0', '2023-Jun-27'),
('9658745', 'Natures Secrets', '47736.0', '2023-Jul-04');

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

CREATE TABLE `items` (
  `itemId` varchar(255) NOT NULL,
  `itemName` varchar(255) NOT NULL,
  `companyName` varchar(100) NOT NULL,
  `catagory` varchar(255) NOT NULL,
  `unit` varchar(20) NOT NULL,
  `shopMax` varchar(255) NOT NULL,
  `storeMax` varchar(255) NOT NULL,
  `reOrderLevel` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `items`
--

INSERT INTO `items` (`itemId`, `itemName`, `companyName`, `catagory`, `unit`, `shopMax`, `storeMax`, `reOrderLevel`) VALUES
('Pdct-0000001', 'NS PAPAYA FW 100ML', 'Natures Secrets', 'Facewash', '100 ml', '6', '10', '8'),
('Pdct-0000002', 'JNT WILD TUMURIC SCRUB', 'Janet', 'Scrub', 'tub', '4', '8', '5'),
('Pdct-0000004', '4EVER KK FW 100 ML', '4 Ever', 'Facewash', '100 ml', '6', '10', '8'),
('Pdct-0000005', 'NS CARROT FW 100 ML', 'Natures Secrets', 'Facewash', '100 ml', '6', '10', '8'),
('Pdct-0000006', 'NS MINT FW 100 ML', 'Natures Secrets', 'Facewash', '100 ml', '6', '10', '8'),
('Pdct-0000007', 'NS LOTUS FW 100 ML', 'Natures Secrets', 'Facewash', '100 ml', '6', '10', '8'),
('Pdct-0000008', 'NS CUCUMBER FW 100 ML', 'Natures Secrets', 'Facewash', '100 ml', '6', '8', '10'),
('Pdct-0000009', '4EVER T TREE FW 100 ML', '4 Ever', 'Facewash', '100 ml', '6', '8', '10'),
('Pdct-0000010', 'kudu', 'lady Bird', 'Scrub', 'g', '200', '500', '100'),
('Pdct-0000011', 'NS MINT FW 100 ML', 'Natures Secrets', 'Facewash', '100 ml', '6', '12', '10'),
('Pdct-0000012', 'NS ALOE CLNSER 100 ML', 'Natures Secrets', 'clenser', '100 ml', '11', '19', '7'),
('Pdct-0000013', 'NS LOTUS FW 100 ML', 'Natures Secrets', 'Facewash', '100 ml', '6', '12', '7'),
('Pdct-0000014', 'JNT FIRNESS FW 100 ML', 'Janet', 'Facewash', '100 ml', '6', '8', '5');

-- --------------------------------------------------------

--
-- Table structure for table `logindetails`
--

CREATE TABLE `logindetails` (
  `userID` int(20) NOT NULL,
  `uLastName` varchar(50) NOT NULL,
  `userName` varchar(50) NOT NULL,
  `userType` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `logindetails`
--

INSERT INTO `logindetails` (`userID`, `uLastName`, `userName`, `userType`) VALUES
(1, 'Nijaadh', 'Admin', 'User'),
(38, 'Ravishka', 'chalana', 'User');

-- --------------------------------------------------------

--
-- Table structure for table `maininventory`
--

CREATE TABLE `maininventory` (
  `itemId` varchar(255) NOT NULL,
  `grnNo` varchar(255) NOT NULL,
  `company` varchar(255) NOT NULL,
  `itemName` varchar(255) NOT NULL,
  `catagory` varchar(255) NOT NULL,
  `unit` varchar(255) NOT NULL,
  `freeIssue` varchar(255) NOT NULL,
  `discount` varchar(255) NOT NULL,
  `lastAddedQty` varchar(255) NOT NULL,
  `unitCost` varchar(255) NOT NULL,
  `unitRetail` varchar(255) NOT NULL,
  `totalCost` varchar(255) NOT NULL,
  `totalValue` varchar(255) NOT NULL,
  `totalQty` varchar(255) NOT NULL,
  `shopAvailable` varchar(255) NOT NULL,
  `store1Available` varchar(255) NOT NULL,
  `store2Available` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `maininventory`
--

INSERT INTO `maininventory` (`itemId`, `grnNo`, `company`, `itemName`, `catagory`, `unit`, `freeIssue`, `discount`, `lastAddedQty`, `unitCost`, `unitRetail`, `totalCost`, `totalValue`, `totalQty`, `shopAvailable`, `store1Available`, `store2Available`) VALUES
('Pdct-0000001', '562', 'Natures Secrets', 'NS PAPAYA FW 100ML', 'Facewash', '100 ml', '12', '15.0', '60', '234.6', '345', '112608.0', '165600.0', '480', '6', '10', '464'),
('Pdct-0000004', '122365489657', '4 Ever', '4EVER KK FW 100 ML', 'Facewash', '100 ml', '12', '15.0', '60', '265.2', '390', '15912.0', '23400.0', '60', '6', '10', '44'),
('Pdct-0000012', '122365489657', 'Natures Secrets', 'NS ALOE CLNSER 100 ML', 'clenser', '100 ml', '12', '15.0', '60', '265.2', '390', '31824.0', '46800.0', '120', '11', '19', '90');

-- --------------------------------------------------------

--
-- Table structure for table `nitmaxstock`
--

CREATE TABLE `nitmaxstock` (
  `itemId` varchar(255) NOT NULL,
  `storeId` varchar(255) NOT NULL,
  `storeName` varchar(255) NOT NULL,
  `maxStock` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `paymentId` varchar(255) NOT NULL,
  `cusId` varchar(255) NOT NULL,
  `billValue` varchar(255) NOT NULL,
  `billCost` varchar(255) NOT NULL,
  `paymentMethod` varchar(255) NOT NULL,
  `Date` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`paymentId`, `cusId`, `billValue`, `billCost`, `paymentMethod`, `Date`) VALUES
('MLTP-0000001', 'C-0000000', '345.0', '234.6', 'Cash', '2023-Jul-04'),
('MLTP-0000002', 'C-0000000', '0.00', '0.00', 'Cash', '2023-Jul-04'),
('MLTP-0000003', 'C-0000000', '345.0', '234.6', 'Cash', '2023-Jul-04'),
('MLTP-0000004', 'C-0000000', '345.0', '234.6', 'Card', '2023-Jul-04'),
('MLTP-0000005', 'C-0000000', '345.0', '234.6', 'Card', '2023-Jul-04');

-- --------------------------------------------------------

--
-- Table structure for table `reoderlevel`
--

CREATE TABLE `reoderlevel` (
  `itemId` varchar(255) NOT NULL,
  `itemName` varchar(255) NOT NULL,
  `reOderLevel` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `returntable`
--

CREATE TABLE `returntable` (
  `no` int(255) NOT NULL,
  `companyId` varchar(255) NOT NULL,
  `companyName` varchar(255) NOT NULL,
  `catagory` varchar(255) NOT NULL,
  `itemId` varchar(255) NOT NULL,
  `itemName` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  `lastAddedQry` varchar(255) NOT NULL,
  `qty` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `returntable`
--

INSERT INTO `returntable` (`no`, `companyId`, `companyName`, `catagory`, `itemId`, `itemName`, `location`, `lastAddedQry`, `qty`) VALUES
(6, 'Sup-0000001', 'Natures Secrets', 'Facewash', 'Pdct-0000001', 'NS PAPAYA FW 100ML', 'Shop', '', '3'),
(7, 'Sup-0000001', 'Natures Secrets', 'Facewash', 'Pdct-0000001', 'NS PAPAYA FW 100ML', 'Store 1', '', '4'),
(8, 'Sup-0000001', 'Natures Secrets', 'Facewash', 'Pdct-0000005', 'NS CARROT FW 100 ML', 'Store 1', '', '2'),
(9, 'Sup-0000004', '4 Ever', 'Facewash', 'Pdct-0000004', '4EVER KK FW 100 ML', 'Store 2', '3', '7'),
(10, 'Sup-0000001', 'Natures Secrets', 'clenser', 'Pdct-0000012', 'NS ALOE CLNSER 100 ML', 'Store 1', '', '6');

-- --------------------------------------------------------

--
-- Table structure for table `shopmaxstock`
--

CREATE TABLE `shopmaxstock` (
  `itemId` varchar(255) NOT NULL,
  `storeId` varchar(255) NOT NULL,
  `storeName` varchar(255) NOT NULL,
  `maxStock` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `supplierfulldetails`
--

CREATE TABLE `supplierfulldetails` (
  `supId` varchar(255) NOT NULL,
  `companyName` varchar(255) NOT NULL,
  `supName` varchar(255) NOT NULL,
  `supContactNo` varchar(255) NOT NULL,
  `supMail` varchar(255) NOT NULL,
  `supAddress` varchar(255) NOT NULL,
  `repFirstName` varchar(255) NOT NULL,
  `repLastName` varchar(255) NOT NULL,
  `repNic` varchar(255) NOT NULL,
  `repGender` varchar(255) NOT NULL,
  `repContactNo` varchar(255) NOT NULL,
  `repMail` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `supplierfulldetails`
--

INSERT INTO `supplierfulldetails` (`supId`, `companyName`, `supName`, `supContactNo`, `supMail`, `supAddress`, `repFirstName`, `repLastName`, `repNic`, `repGender`, `repContactNo`, `repMail`) VALUES
('Sup-0000001', 'Natures Secrets', 'Edith Enterprice', '0774411765', 'mohomednijaadh.net@gmail', 'nit', 'Nishshaanka ', 'Perera', '200021701711', 'Male', '0774411765', 'mohomednijaadh.net@gmail'),
('Sup-0000002', 'Janet', 'Kandana Distribute', '0774411765', 'janet@gmail.com', 'Kandana', 'Mahanama', 'Perera', '300021701700', 'Male', '0112236587', 'mahanma@gmail.com'),
('Sup-0000003', 'Amrin', 'Leaves Lanka', '0774411765', 'amrin@gmailcom', 'Gall', 'lawanya', 'farnanndo', '200030021701744', 'Female', 'lawanya@gmail.com', 'lawanya@gmail.com'),
('Sup-0000004', '4 Ever', 'Karunanayake Distributes', '0774411765', 'forever@gmail.com', 'kahabilihena', 'ravindu ', 'prabashwara', '200021701711', 'Male', '077441175', 'ravindu@gmail'),
('Sup-0000005', 'lady Bird', 'paneesha', '0774411765', 'pani@gmail.com', 'kudukarawatta,kirindiwala', 'tharushika', 'vidyarathne', '1982102030v', 'Male', '0221133456', 'fddfad');

-- --------------------------------------------------------

--
-- Table structure for table `unit`
--

CREATE TABLE `unit` (
  `unitId` varchar(255) NOT NULL,
  `unitName` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `unit`
--

INSERT INTO `unit` (`unitId`, `unitName`) VALUES
('Unit-0000001', '1 Kg'),
('Unit-0000002', 'g'),
('Unit-0000003', '1 L'),
('Unit-0000004', '100 ml'),
('Unit-0000005', 'tub');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `uFirstName` varchar(50) NOT NULL,
  `uLastName` varchar(50) NOT NULL,
  `uNic` varchar(50) NOT NULL,
  `uGender` varchar(8) NOT NULL,
  `uDateOfBirth` blob NOT NULL,
  `uContactNo` varchar(12) NOT NULL,
  `uEmContactNo` varchar(12) NOT NULL,
  `uEmail` varchar(50) NOT NULL,
  `uAddress` varchar(100) NOT NULL,
  `uUserName` varchar(20) NOT NULL,
  `uPassword` varchar(20) NOT NULL,
  `uSecQuistion` varchar(50) NOT NULL,
  `uSecAnswer` varchar(50) NOT NULL,
  `userType` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`uFirstName`, `uLastName`, `uNic`, `uGender`, `uDateOfBirth`, `uContactNo`, `uEmContactNo`, `uEmail`, `uAddress`, `uUserName`, `uPassword`, `uSecQuistion`, `uSecAnswer`, `userType`) VALUES
('Mohomed', 'Nijaadh', '200021701711', 'Male', 0x323030302c30382c3034, '0774411765', '0766570486', 'mohomednijaadh.net@gmail.com', '61/29Rupasinghe garden,\nThihariya,\nkalagedihena.', 'Admin', '0040', 'who is your faviorite person ?', 'Amma', 'User'),
('Chalana', 'Ravishka', '300021701711', 'Male', 0x323030302c31312c3032, '0774411765', '077366559', 'chalana@gmail', 'No25/A\nMadagampitiya\nDiwulapitiya', 'chalana', '123', 'what is your Faviorite Colour ?', 'blue', 'User');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `catagory`
--
ALTER TABLE `catagory`
  ADD PRIMARY KEY (`catagoryId`);

--
-- Indexes for table `company`
--
ALTER TABLE `company`
  ADD PRIMARY KEY (`companyId`) USING BTREE;

--
-- Indexes for table `cusfewdetails`
--
ALTER TABLE `cusfewdetails`
  ADD PRIMARY KEY (`cusId`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`cusId`);

--
-- Indexes for table `expiredate`
--
ALTER TABLE `expiredate`
  ADD PRIMARY KEY (`no`);

--
-- Indexes for table `grn`
--
ALTER TABLE `grn`
  ADD PRIMARY KEY (`grnNo`);

--
-- Indexes for table `items`
--
ALTER TABLE `items`
  ADD PRIMARY KEY (`itemId`);

--
-- Indexes for table `logindetails`
--
ALTER TABLE `logindetails`
  ADD PRIMARY KEY (`userID`,`userName`);

--
-- Indexes for table `maininventory`
--
ALTER TABLE `maininventory`
  ADD PRIMARY KEY (`itemId`);

--
-- Indexes for table `nitmaxstock`
--
ALTER TABLE `nitmaxstock`
  ADD PRIMARY KEY (`storeId`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`paymentId`);

--
-- Indexes for table `reoderlevel`
--
ALTER TABLE `reoderlevel`
  ADD PRIMARY KEY (`itemId`);

--
-- Indexes for table `returntable`
--
ALTER TABLE `returntable`
  ADD PRIMARY KEY (`no`);

--
-- Indexes for table `shopmaxstock`
--
ALTER TABLE `shopmaxstock`
  ADD PRIMARY KEY (`storeId`);

--
-- Indexes for table `supplierfulldetails`
--
ALTER TABLE `supplierfulldetails`
  ADD PRIMARY KEY (`supId`);

--
-- Indexes for table `unit`
--
ALTER TABLE `unit`
  ADD PRIMARY KEY (`unitId`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`uUserName`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `logindetails`
--
ALTER TABLE `logindetails`
  MODIFY `userID` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `returntable`
--
ALTER TABLE `returntable`
  MODIFY `no` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
