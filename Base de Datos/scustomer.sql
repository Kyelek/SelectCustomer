-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-05-2023 a las 22:45:47
-- Versión del servidor: 10.4.24-MariaDB
-- Versión de PHP: 7.4.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `scustomer`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id` int(11) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `apellidos` varchar(30) NOT NULL,
  `domicilio` varchar(40) NOT NULL,
  `telefono` int(10) NOT NULL,
  `email` varchar(45) NOT NULL,
  `producto` text NOT NULL,
  `precio` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id`, `nombre`, `apellidos`, `domicilio`, `telefono`, `email`, `producto`, `precio`) VALUES
(1, 'Fran', 'Ibeas', 'calle Julio Verne n24', 679694585, 'fco.eugenio@hotmail.', 'barbacoa 3\ncarne 4', 7),
(3, 'Joso', 'El Joso', 'Joso', 123456789, 'joso@joso.joso', 'Chicle: 5\ncarbon : 10', 15),
(12, 'Prueba', 'De Prueba', 'calle falsa 123', 123123125, 'unemail@hola.com', 'cocacola: 2\nlapiz: 3\npizza : 10\ntoldo: 300\nbarbacoa: 500\nluces: 5\ncaramelo: 8\nbaticao : 10\nmorcilla: 3\nOrdenador : 1500\nraton: 10\nteclado: 5\nPantalla: 80\n', 2436),
(13, 'Eugenio', 'Ibeas', 'calle una 123', 123234456, 'eugenio@algo.com', 'Cocacola : 3\nnestia: 4\nbarbacoa : 80\nchorizo: 10\nmorcilla: 10\npaquete de tabaco : 500', 607);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `login`
--

CREATE TABLE `login` (
  `id` int(11) NOT NULL,
  `usuario` varchar(20) NOT NULL,
  `contrasena` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `login`
--

INSERT INTO `login` (`id`, `usuario`, `contrasena`) VALUES
(1, 'Fran', 'Admin123'),
(2, 'Bego', 'Arbol123'),
(3, 'Joso', 'Joso123'),
(4, 'victor', 'gurrubi'),
(5, 'Prueba', 'Admin123'),
(6, 'Eugenio', 'Eugenio123');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `telefono` (`telefono`);

--
-- Indices de la tabla `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `Fran` (`usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `login`
--
ALTER TABLE `login`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
