/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     11/02/2015 09:21:25 a.m.                     */
/*==============================================================*/

drop table if exists DNACTIVIDAD;
drop table if exists DNACTIVIDAD_ECO;
drop table if exists DNBARUNDMED;
drop table if exists DNBITACORA;
drop table if exists DNCARGO;
drop table if exists DNCARRITO_COMPRA;
drop table if exists DNCATEGORIAS_PROYECTOS;
drop table if exists DNCLASIFICACION;
drop table if exists DNCONDIPAGO;
drop table if exists DNCONEXION;
drop table if exists DNCONTACTOS;
drop table if exists DNDEPARTAMENTO;
drop table if exists DNDIRECCION;
drop table if exists DNDOCUMENTOS;
drop table if exists DNEMPRESAS;
drop table if exists DNENCABEZADO;
drop table if exists DNESTADOS;
drop table if exists DNGRUPO;
drop table if exists DNINVENTARIO;
drop table if exists DNLISTPRE;
drop table if exists DNMAESTRO;
drop table if exists DNMENU;
drop table if exists DNMONEDA;
drop table if exists DNMUNICIPIOS;
drop table if exists DNOBJ_ESPECIFICO;
drop table if exists DNOBJ_PRINCIPAL;
drop table if exists DNPAIS;
drop table if exists DNPARROQUIAS;
drop table if exists DNPERMIACCIONES;
drop table if exists DNPERMISOLOGIA;
drop table if exists DNPERMISO_GRUPAL;
drop table if exists DNPERMISO_USUARIOS;
drop table if exists DNPERSONAS;
drop table if exists DNPRECIO;
drop table if exists DNPRODUCTO;
drop table if exists DNPROYECTOS;
drop table if exists DNRELPROSUB;
drop table if exists DNREL_OBJP_OBJE;
drop table if exists DNREL_OBJP_PROY;
drop table if exists DNROL;
drop table if exists DNSUBGRUPO;
drop table if exists DNSUBSECTOR;
drop table if exists DNTAREAS;
drop table if exists DNTAREAS_AUTOMATICAS;
drop table if exists DNTIPCONTACTO;
drop table if exists DNTIPIVA;
drop table if exists DNTIPOMAESTRO;
drop table if exists DNUNDMEDIDA;
drop table if exists DNUSUARIOS;
drop table if exists DNVENDEDOR;

/*==============================================================*/
/* Table: DNACTIVIDAD                                           */
/*==============================================================*/
create table DNACTIVIDAD
(
   ACTIV_ID             int not null auto_increment,
   ACTI_DESCRIP         char(180),
   ACTIV_FECHA_I        date,
   ACTIV_FECHA_F        date,
   ACTIV_ID_OBJ_ESP     int,
   primary key (ACTIV_ID)
);

/*==============================================================*/
/* Table: DNACTIVIDAD_ECO                                       */
/*==============================================================*/
create table DNACTIVIDAD_ECO
(
   ACT_EMPRESA          char(6),
   ACT_ID               int(11) not null auto_increment comment 'Campo de ID de la Tabla',
   ACT_DATTIM           timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   ACT_USER             char(10) comment 'Usuario que Ingreso el Registro',
   ACT_MACPC            char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   ACT_CODIGO           char(6) not null,
   ACT_NOMBRE           char(80),
   ACT_ACTIVO           bool,
   primary key (ACT_ID)
);

alter table DNACTIVIDAD_ECO comment 'Tabla de Actividad del Proveedor o Cliente';

/*==============================================================*/
/* Index: DNACTIVIDAD_INDEX_1                                   */
/*==============================================================*/
create unique index DNACTIVIDAD_INDEX_1 on DNACTIVIDAD_ECO
(
   ACT_CODIGO
);

/*==============================================================*/
/* Table: DNBARUNDMED                                           */
/*==============================================================*/
create table DNBARUNDMED
(
   BAR_EMPRESA          char(6),
   BAR_ID               int(11) not null auto_increment comment 'Campo de ID de la Tabla',
   BAR_DATTIM           timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   BAR_USER             char(10) comment 'Usuario que Ingreso el Registro',
   BAR_MACPC            char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   BAR_CODUND           char(8),
   BAR_CODPRO           char(15),
   BAR_BARRA            char(15) not null,
   primary key (BAR_ID)
);

/*==============================================================*/
/* Table: DNBITACORA                                            */
/*==============================================================*/
create table DNBITACORA
(
   BIT_EMPRESA          char(6),
   BIT_ID               int(11) not null auto_increment,
   BIT_MACPC            char(20),
   BIT_CODUSER          int(11),
   BIT_USUARIO          char(50),
   BIT_DATE             timestamp,
   BIT_ACCION           char(80),
   BIT_DETALLES         char(255),
   BIT_NAVEGADOR        char(18),
   BIT_APPORG           char(50),
   primary key (BIT_ID)
);

/*==============================================================*/
/* Index: DNBITACORA_INDEX_1                                    */
/*==============================================================*/
create index DNBITACORA_INDEX_1 on DNBITACORA
(
   BIT_USUARIO
);

/*==============================================================*/
/* Table: DNCARGO                                               */
/*==============================================================*/
create table DNCARGO
(
   CAR_ID               int not null auto_increment,
   CAR_NOMBRE           char(80),
   CAR_ID_PERSON        int,
   primary key (CAR_ID)
);

/*==============================================================*/
/* Table: DNCARRITO_COMPRA                                      */
/*==============================================================*/
create table DNCARRITO_COMPRA
(
   CAC_EMPRESA          char(6),
   CAC_ID               int not null auto_increment,
   CAC_MACPC            char(20),
   CAC_CODUSU           int(11),
   CAC_CODPRO           char(15),
   CAN_ITEM             int(11),
   CAN_FECHA            timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (CAC_ID)
);

/*==============================================================*/
/* Table: DNCATEGORIAS_PROYECTOS                                */
/*==============================================================*/
create table DNCATEGORIAS_PROYECTOS
(
   CAT_ID               int not null auto_increment,
   CAT_ID_SUBCATE       int,
   CAT_NOMBRE           char(80),
   CAT_DESCRIP          char(160),
   CAT_FECHA            timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (CAT_ID)
);

/*==============================================================*/
/* Table: DNCLASIFICACION                                       */
/*==============================================================*/
create table DNCLASIFICACION
(
   CLA_EMPRESA          char(6),
   CLA_ID               int(11) not null,
   SUB_CLA_ID           int(11),
   CLA_NOMBRE           char(80),
   CLA_ORDEN            int(11),
   primary key (CLA_ID)
);

/*==============================================================*/
/* Table: DNCONDIPAGO                                           */
/*==============================================================*/
create table DNCONDIPAGO
(
   CDI_EMPRESA          char(6),
   CDI_ID               int(11) not null auto_increment comment 'Campo de ID de la Tabla',
   CDI_DATTIM           timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   CDI_USER             char(10) comment 'Usuario que Ingreso el Registro',
   CDI_MACPC            char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   CDI_DESCRI           char(60) not null comment 'Descripcion de la Condicion de Pago',
   CDI_ACTIVO           bool,
   primary key (CDI_ID, CDI_DESCRI)
);

/*==============================================================*/
/* Table: DNCONEXION                                            */
/*==============================================================*/
create table DNCONEXION
(
   CONF_IPPC           CHAR(15) NULL DEFAULT NULL,
   CONF_MACPC          CHAR(20) NULL DEFAULT NULL,
   CONF_IP             CHAR(20) NULL DEFAULT NULL,
   CONF_USER           CHAR(15) NULL DEFAULT NULL,
   CONF_PASS           CHAR(30) NULL DEFAULT NULL,
   CONF_BD             CHAR(30) NULL DEFAULT NULL,
   CONF_MANEJADOR      CHAR(30) NULL DEFAULT NULL,
   CONF_FCHCONF        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

alter table DNCONEXION comment 'Tabla de Configuración de Conexiones';

/*==============================================================*/
/* Index: CDI_DESCRI                                            */
/*==============================================================*/
create unique index CDI_DESCRI on DNCONDIPAGO
(
   CDI_DESCRI
);

/*==============================================================*/
/* Table: DNCONTACTOS                                           */
/*==============================================================*/
create table DNCONTACTOS
(
   CON_EMPRESA          char(6),
   CON_ID               int(11) not null comment 'Campo de ID de la Tabla',
   CON_DATTIM           timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   CON_USER             char(10) comment 'Usuario que Ingreso el Registro',
   CON_MACPC            char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   CON_CODIGO           char(10) not null,
   CON_CODTIPC          char(10) not null,
   CON_CODMAE           char(10) not null,
   CON_CONTAC           char(60),
   CON_ACTIVO           bool,
   primary key (CON_ID)
);

alter table DNCONTACTOS comment 'Tabla de Contactos';

/*==============================================================*/
/* Index: DNCONTACTOS_INDEX_1                                   */
/*==============================================================*/
create unique index DNCONTACTOS_INDEX_1 on DNCONTACTOS
(
   CON_CODIGO
);

/*==============================================================*/
/* Table: DNDEPARTAMENTO                                        */
/*==============================================================*/
create table DNDEPARTAMENTO
(
   DEP_ID               int not null auto_increment,
   DEP_NOMBRE           char(80),
   primary key (DEP_ID)
);

/*==============================================================*/
/* Table: DNDIRECCION                                           */
/*==============================================================*/
create table DNDIRECCION
(
   DIR_EMPRESA          char(6),
   DIR_ID               int(11) not null auto_increment comment 'Campo de ID de la Tabla',
   DIR_DATTIM           timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   DIR_USER             char(10) comment 'Usuario que Ingreso el Registro',
   DIR_MACPC            char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   DIR_CODIGO           char(10) not null,
   DIR_CODPAI           int(11) comment 'Campo de ID de la Tabla',
   DIR_CODEST           int(11),
   DIR_CODMUN           int(11),
   DIR_CODPAR           int(11),
   SBS_ID               int(11),
   DIR_CODSBS           char(10),
   DIR_DESCRI           char(80),
   DIR_ACTIVO           bool,
   primary key (DIR_ID)
);

alter table DNDIRECCION comment 'Tabla de Direcciones';

/*==============================================================*/
/* Index: DNDIRECCION_INDEX_1                                   */
/*==============================================================*/
create unique index DNDIRECCION_INDEX_1 on DNDIRECCION
(
   DIR_CODIGO
);

/*==============================================================*/
/* Table: DNDOCUMENTOS                                          */
/*==============================================================*/
create table DNDOCUMENTOS
(
   DOC_EMPRESA          char(6),
   DOC_ID               int(11) not null auto_increment comment 'Campo de ID de la Tabla',
   DOC_DATTIM           timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   DOC_USER             char(10) comment 'Usuario que Ingreso el Registro',
   DOC_MACPC            char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   DOC_CODIGO           char(3) not null comment 'Codigo del Tipo de Documento',
   DOC_DESCRI           char(80) comment 'Descripcion del Tipo de Documento',
   DOC_CXC              bool comment 'Cuentas por Cobrar',
   DOC_CXP              bool comment 'Cuentas por Pagar',
   DOC_INVACT           char(2),
   DOC_LIBVTA           bool comment 'Se muestra el Documento en el Libro de Vtas',
   DOC_LIBCOM           bool comment 'Se muestra el Documento en el Libro de Compras',
   DOC_PAGOS            bool comment 'Acepta pago el Documento',
   DOC_IVA              bool comment 'Calcula el IVA el documento',
   DOC_RETISLR          bool comment 'Aplica ISLR',
   DOC_RETIVA           bool,
   DOC_FISICO           bool comment 'Mueve el Inventario Fisico',
   DOC_LOGICO           bool comment 'Mueve el Inventario Logico',
   DOC_ACTIVO           bool comment 'Determina si el Documento esta Activo',
   primary key (DOC_ID)
);

/*==============================================================*/
/* Index: DNDOCUMENTOS_INDEX_1                                  */
/*==============================================================*/
create unique index DNDOCUMENTOS_INDEX_1 on DNDOCUMENTOS
(
   DOC_CODIGO
);

/*==============================================================*/
/* Table: DNEMPRESAS                                            */
/*==============================================================*/
create table DNEMPRESAS
(
   EMP_DATTIM           timestamp default CURRENT_TIMESTAMP,
   EMP_USER             char(10),
   EMP_MACPC            char(20),
   EMP_CODIGO           char(6) not null,
   EMP_NOMBRE           char(255),
   EMP_RIF              char(12) not null,
   EMP_DIRECCION        text,
   EMP_ACTIVO           bool,
   primary key (EMP_CODIGO, EMP_RIF)
);

alter table DNEMPRESAS comment 'Tablas para Multi-Empresas';

/*==============================================================*/
/* Index: DNEMPRESAS_PK                                         */
/*==============================================================*/
create unique index DNEMPRESAS_PK on DNEMPRESAS
(
   EMP_CODIGO,
   EMP_RIF
);

/*==============================================================*/
/* Table: DNENCABEZADO                                          */
/*==============================================================*/
create table DNENCABEZADO
(
   ENC_EMPRESA          char(6),
   ENC_ID               int(11) not null auto_increment comment 'Campo de ID de la tabla',
   ENC_DATTIM           timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   ENC_USER             char(10) comment 'Usuario que Ingreso el Registro',
   ENC_MACPC            char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   ENC_CODIGO           char(15) not null comment 'Numero del Documento',
   ENC_CODDOC           char(3) comment 'Codigo del Tipo de Documento',
   ENC_CODMAE           char(10) comment 'Codigo del Maestro (Cliente / Proveedor)',
   ENC_ACTIVO           bool comment 'Estado del Documento 1 Activo 0 Inactivo',
   ENC_NROFIS           char(10) comment 'Numero de Control Fiscal del Documento',
   ENC_BASE             numeric(12,2) comment 'Monto Base del Documento',
   ENC_IVA              numeric(12,2) comment 'Iva del Documento',
   ENC_MONTO            numeric(12,2) comment 'Total del Documento',
   ENC_DCTO             numeric(5,2) comment 'Descuento del Documento',
   ENC_CONDIC           char(60) comment 'Condicion del Documento',
   ENC_CODVEN           char(8),
   ENC_DIAVEN           numeric(3,0) comment 'Dias de Vencimiento del Documento',
   ENC_FECHA            date comment 'Fecha de Registro del Documento',
   ENC_FCHDEC           date comment 'Fecha de Declaracion del Documento',
   ENC_FCHVEN           date comment 'Fecha de Vencimiento del Documento',
   ENC_ZOXVEN           char(10),
   primary key (ENC_ID)
);

/*==============================================================*/
/* Index: DNENCABEZADO_INDEX_1                                  */
/*==============================================================*/
create index DNENCABEZADO_INDEX_1 on DNENCABEZADO
(
   ENC_CODIGO
);

/*==============================================================*/
/* Table: DNESTADOS                                             */
/*==============================================================*/
create table DNESTADOS
(
   EST_EMPRESA          char(6),
   EST_ID               int(11) not null auto_increment comment 'Campo de ID de la Tabla',
   EST_DATTIM           timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   EST_USER             char(10) comment 'Usuario que Ingreso el Registro',
   EST_MACPC            char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   EST_CODPAI           int(11),
   EST_NOMBRE           char(60),
   EST_ACTIVO           bool,
   primary key (EST_ID)
);

/*==============================================================*/
/* Table: DNGRUPO                                               */
/*==============================================================*/
create table DNGRUPO
(
   GRU_ID               int(11) not null auto_increment comment 'Campo de ID de la Tabla',
   GRU_DATTIM           timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   GRU_USER             char(10) comment 'Usuario que Ingreso el Registro',
   GRU_MACPC            char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   GRU_CODIGO           char(10) not null comment 'Codigo del Grupo',
   GRU_DESCRI           char(60) comment 'Nombre del Grupo',
   GRU_ACTIVO           bool,
   primary key (GRU_ID)
);

alter table DNGRUPO comment 'Tabla de Grupos';

/*==============================================================*/
/* Index: DNGRUPO_INDEX_1                                       */
/*==============================================================*/
create index DNGRUPO_INDEX_1 on DNGRUPO
(
   GRU_CODIGO
);

/*==============================================================*/
/* Table: DNINVENTARIO                                          */
/*==============================================================*/
create table DNINVENTARIO
(
   INV_EMPRESA          char(6),
   INV_ID               int(11) not null auto_increment comment 'Campo de ID de la Tabla',
   INV_DATTIM           timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   INV_USER             char(10) comment 'Usuario que Ingreso el Registro',
   INV_MACPC            char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   INV_CODDOC           char(3) comment 'Codigo del Tipo de Documento',
   INV_CODPRO           char(15) comment 'Codigo del Producto',
   INV_CODENC           char(15) comment 'Numero del Documento (Encabezado)',
   INV_CODMAE           char(10) comment 'Codigo del Maestro (Cliente / Proveedor)',
   INV_UNIDAD           char(8) comment 'Codigo de la Unidad de Medida',
   INV_CANUND           numeric(12,2),
   INV_LOGICO           numeric(1,0) comment 'Valor para determinar si es una entrada (1) o salida (-1)',
   INV_FISICO           numeric(1,0) comment 'Valor para determinar si es una entrada (1) o salida (-1)',
   INV_ITEM             char(6) comment 'Numero de Items del Documento',
   INV_CANTID           numeric(12,2) comment 'Candida del Producto',
   INV_PRECIO           numeric(12,2) comment 'Precio del Producto',
   INV_TOTAL            numeric(12,2) comment 'Total del Producto (Cant x Prec)',
   INV_ACTIVO           bool comment 'Determina si el Item esta Activo en el Documento.',
   primary key (INV_ID)
);

/*==============================================================*/
/* Table: DNLISTPRE                                             */
/*==============================================================*/
create table DNLISTPRE
(
   LIS_ID               int(11) not null auto_increment comment 'Campo de ID de la Tabla',
   LIS_DATTIM           timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   LIS_USER             char(10) comment 'Usuario que Ingreso el Registro',
   LIS_MACPC            char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   LIS_CODIGO           char(8) not null comment 'Codigo de la Lista de Precios',
   LIS_DESCRI           char(40) comment 'Nombre de la Lista de Precio',
   LIS_ACTIVO           bool,
   primary key (LIS_ID)
);

/*==============================================================*/
/* Index: DNLISTPRE_INDEX_1                                     */
/*==============================================================*/
create unique index DNLISTPRE_INDEX_1 on DNLISTPRE
(
   LIS_CODIGO
);

/*==============================================================*/
/* Table: DNMAESTRO                                             */
/*==============================================================*/
create table DNMAESTRO
(
   MAE_EMPRESA          char(6),
   MAE_ID               int(11) not null auto_increment comment 'Campo de ID de la Tabla',
   MAE_DATTIM           timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   MAE_USER             char(10) comment 'Usuario que Ingreso el Registro',
   MAE_MACPC            char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   MAE_CODIGO           char(10) not null,
   MAE_CODTIPM          char(10),
   MAE_CODMON           char(6),
   MAE_CODACT           char(6),
   MAE_CODDIR           char(10),
   MAE_NOMBRE           char(120),
   MAE_RAZON            char(20),
   MAE_LIMITE           numeric(10,2),
   MAE_DCTO             numeric(5,2),
   MAE_CONDIC           char(60),
   MAE_CODLIS           char(8) comment 'Codigo de la Lista de Precios',
   MAE_ACTIVO           bool,
   MAE_DIAS             numeric(3,0),
   MAE_FECHA            date,
   MAE_OBSERV           longtext,
   MAE_RIF              char(12),
   MAE_NIT              char(12),
   MAE_CONTRI           char(2),
   MAE_TIPPER           char(1),
   MAE_RESIDE           char(2),
   MAE_ZONCOM           char(30),
   MAE_OTRMON           char(6),
   MAE_CONTESP          char(8),
   MAE_RETIVA           int(3),
   MAE_MTOVEN           numeric(12,2),
   MAE_DIAVEN           numeric(3,0),
   MAE_EMAIL            char(120),
   MAE_CLIENTE          bool,
   MAE_PROVEED          bool,
   MAE_CLIENPOS         bool,
   MAE_FOTO             mediumblob,
   primary key (MAE_ID)
);

alter table DNMAESTRO comment 'Tabla de Maestros de Clientes y Proveedores';

/*==============================================================*/
/* Index: DNMAESTRO_INDEX_1                                     */
/*==============================================================*/
create unique index DNMAESTRO_INDEX_1 on DNMAESTRO
(
   MAE_CODIGO
);

/*==============================================================*/
/* Table: DNMENU                                                */
/*==============================================================*/
create table DNMENU
(
   MEN_EMPRESA          char(6) not null,
   MEN_ID               int(11) not null auto_increment,
   SUB_MEN_ID           numeric(11,0) not null,
   MEN_NOMBRE           char(50),
   MEN_DESCRIPCION      char(80),
   MEN_URL              char(200),
   MEN_ORDEN            numeric(11,0),
   MEN_TIPO             numeric(11,0) not null,
   MEN_IMG              char(100),
   MEN_IMGMENU          char(20),
   MEN_FECHA            date,
   MEN_HORA             time,
   MEN_EXTERNO          char(2),
   MEN_URLAMIGABLE      text,
   MEN_ICON             varchar(150),
   MEN_ESTMENU          tinyint(1) null default null,
   primary key (MEN_ID)
);

/*==============================================================*/
/* Table: DNMONEDA                                              */
/*==============================================================*/
create table DNMONEDA
(
   MON_EMPRESA          char(6),
   MON_ID               int(11) not null auto_increment comment 'Campo de ID de la Tabla',
   MON_DATTIM           timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   MON_USER             char(10) comment 'Usuario que Ingreso el Registro',
   MON_MACPC            char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   MON_CODIGO           char(6) not null,
   MON_NOMBRE           char(40),
   MON_NOMENC           char(6),
   MON_VALOR            numeric(8,2),
   MON_FCHVIG           date,
   MON_ACTIVO           bool,
   primary key (MON_ID)
);

alter table DNMONEDA comment 'Tabla de Monedas';

/*==============================================================*/
/* Index: DNMONEDA_INDEX_1                                      */
/*==============================================================*/
create unique index DNMONEDA_INDEX_1 on DNMONEDA
(
   MON_CODIGO
);

/*==============================================================*/
/* Table: DNMUNICIPIOS                                          */
/*==============================================================*/
create table DNMUNICIPIOS
(
   MUN_EMPRESA          char(6) not null,
   MUN_ID               int(11) not null auto_increment comment 'Campo de ID de la Tabla',
   MUN_DATTIM           timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   MUN_USER             char(10) comment 'Usuario que Ingreso el Registro',
   MUN_MACPC            char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   MUN_CODEST           int(11) not null,
   MUN_NOMBRE           char(60),
   MUN_ACTIVO           bool,
   primary key (MUN_ID),
   key AK_MUN_CODIGO (MUN_ID)
);

/*==============================================================*/
/* Table: DNOBJ_ESPECIFICO                                      */
/*==============================================================*/
create table DNOBJ_ESPECIFICO
(
   OBJE_ID              int not null auto_increment,
   OBJE_ID_OBJ_PRI      int,
   OBJE_NOMBRE          char(80),
   OBJE_DESCRIP         char(160),
   primary key (OBJE_ID)
);

/*==============================================================*/
/* Table: DNOBJ_PRINCIPAL                                       */
/*==============================================================*/
create table DNOBJ_PRINCIPAL
(
   OBJP_ID              int not null,
   OBJP_NOMBRE          char(80),
   OBJP_DESCRIP         char(160),
   OBJP_PROYECTO        int,
   primary key (OBJP_ID)
);

/*==============================================================*/
/* Table: DNPAIS                                                */
/*==============================================================*/
create table DNPAIS
(
   PAI_EMPRESA          char(6),
   PAI_ID               int(11) not null auto_increment comment 'Campo de ID de la Tabla',
   PAI_DATTIM           timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   PAI_USER             char(10) comment 'Usuario que Ingreso el Registro',
   PAI_MACPC            char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   PAI_NOMBRE           char(60),
   PAI_CONTIN           char(30),
   PAI_ABRPAI           char(3),
   PAI_ACTIVO           bool,
   primary key (PAI_ID)
);

/*==============================================================*/
/* Table: DNPARROQUIAS                                          */
/*==============================================================*/
create table DNPARROQUIAS
(
   PAR_EMPRESA          char(6),
   PAR_ID               int(11) not null auto_increment comment 'Campo de ID de la Tabla',
   PAR_DATTIM           timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   PAR_USER             char(10) comment 'Usuario que Ingreso el Registro',
   PAR_MACPC            char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   PAR_NOMBRE           char(60),
   PAR_CODMUN           int(11),
   PAR_ACTIVO           bool,
   primary key (PAR_ID)
);

/*==============================================================*/
/* Table: DNPERMIACCIONES                                       */
/*==============================================================*/
create table DNPERMIACCIONES
(
   ACC_EMPRESA          char(6),
   ACC_ID               char(50) not null,
   ACC_PERMISO          int(11),
   ACC_INCLUIR          numeric(1,0),
   ACC_CONSULTAR        numeric(1,0),
   ACC_MODIFICAR        numeric(1,0),
   ACC_ELIMINAR         numeric(1,0),
   ACC_IMPRIMIR         numeric(1,0),
   ACC_EXTRA1           numeric(1,0),
   ACC_EXTRA2           numeric(1,0),
   primary key (ACC_ID)
);

/*==============================================================*/
/* Table: DNPERMISOLOGIA                                        */
/*==============================================================*/
create table DNPERMISOLOGIA
(
   MIS_EMPRESA          char(6),
   MIS_ID               char(50) not null,
   MIS_PERMISO          int(11),
   MIS_MENU             int(11),
   MIS_TIPOMENU         numeric(11,0),
   MIS_TIPOPER          numeric(11,0),
   MIS_ACTIVO           bool,
   primary key (MIS_ID)
);

/*==============================================================*/
/* Table: DNPERMISO_GRUPAL                                      */
/*==============================================================*/
create table DNPERMISO_GRUPAL
(
   PER_EMPRESA          char(6),
   PER_ID               int(11) not null auto_increment,
   PER_NOMBRE           char(100),
   PER_DESCRIPCION      char(200),
   PER_TIPO             char(1),
   PER_ACTIVO           bool,
   PER_FECHA            date,
   PER_HORA             time,
   PER_TIPOMENU         char(1),
   primary key (PER_ID)
);

/*==============================================================*/
/* Table: DNPERMISO_USUARIOS                                    */
/*==============================================================*/
create table DNPERMISO_USUARIOS
(
   PERUS_EMPRESA        char(6),
   PERUS_ID             int(11) not null auto_increment,
   PERUS_USUARIO        char(20),
   PERUS_NOMBRE         char(100),
   PERUS_DESCRIPCION    char(200),
   PERUS_TIPO           char(1),
   PERUS_ACTIVO         bool,
   PERUS_FECHA          date,
   PERUS_HORA           time,
   PERUS_TIPOMENU       char(1),
   primary key (PERUS_ID)
);

/*==============================================================*/
/* Table: DNPERSONAS                                            */
/*==============================================================*/
create table DNPERSONAS
(
   PERS_ID              int not null auto_increment,
   PERS_ID_DEPART       int,
   PERS_NOMBRE          char(80),
   PERS_APELLIDO        char(80),
   primary key (PERS_ID)
);

/*==============================================================*/
/* Table: DNPRECIO                                              */
/*==============================================================*/
create table DNPRECIO
(
   PRE_EMPRESA          char(6),
   PRE_ID               int(11) not null auto_increment comment 'Campo de ID de la Tabla',
   PRE_DATTIM           timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   PRE_USER             char(10) comment 'Usuario que Ingreso el Registro',
   PRE_MACPC            char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   PRE_CODIGO           char(10) not null,
   PRE_CODPRO           char(15),
   PRE_CODLIS           char(8),
   PRE_MONTO            numeric(12,2),
   PRE_CODUNA           char(8),
   PRE_FCHVEN           date,
   PRE_ACTIVO           bool,
   primary key (PRE_ID)
);

alter table DNPRECIO comment 'Tabla de Precios del Producto';

/*==============================================================*/
/* Table: DNPRODUCTO                                            */
/*==============================================================*/
create table DNPRODUCTO
(  
	PRO_EMPRESA          char(6),
   PRO_ID               int(11) not null auto_increment comment 'Campo de ID de la Tabla',
   PRO_DATTIM           timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   PRO_USER             char(10) comment 'Usuario que Ingreso el Registro',
   PRO_MACPC            char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   PRO_CODIGO           char(15) not null comment 'Codigo del Producto',
   PRO_NOMBRE           char(80) not null comment 'Nombre del Producto',
   PRO_DESCRI           text comment 'Descripcion del Producto',
   PRO_TIPPREC          char(1) not null,
 	PRO_CODUND           char(3) not null,
   PRO_UTILIZ           char(3) comment 'Utilizacion del Producto (Compra, Ventas, Servicio, etc)',
   PRO_TIPIVA           char(3) not null,
   PRO_RUTAIMG          char(10) comment 'Codigo de Relacion del Producto con el Sub-Grupo',
   PRO_ACTIVO           bool,
   primary key (PRO_ID)
);

alter table DNPRODUCTO comment 'Tabla de Productos';

/*==============================================================*/
/* Index: DNPRODUCTO_INDEX_1                                    */
/*==============================================================*/
create index DNPRODUCTO_INDEX_1 on DNPRODUCTO
(
   PRO_CODIGO
);

/*==============================================================*/
/* Table: DNPROYECTOS                                           */
/*==============================================================*/
create table DNPROYECTOS
(
   PROY_ID              int not null auto_increment,
   PROY_NOMBRE          char(80),
   PROY_DESCRI          char(160),
   PROY_FECHA_I         date,
   PROY_FECHA_F         date,
   PROY_ID_CATEG        int,
   PROY_CODMAE          char(10),
   PROY_LOCALIZACION    text,
   primary key (PROY_ID)
);

/*==============================================================*/
/* Table: DNRELPROSUB                                           */
/*==============================================================*/
create table DNRELPROSUB
(
   REL_ID               int(11) not null auto_increment comment 'Campo de ID de la Tabla',
   REL_DATTIM           timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   REL_USER             char(10) comment 'Usuario que Ingreso el Registro',
   REL_MACPC            char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   REL_CODIGO           char(10) not null,
   REL_CODPRO           char(15) not null comment 'Codigo del Producto',
   REL_CODSUB           char(10) comment 'Codigo del Sub-Grupo',
   primary key (REL_ID, REL_CODIGO),
   key AK_REL_CODPRO (REL_CODPRO, REL_ID)
);

alter table DNRELPROSUB comment 'Codigo de la Relacion del Producto con el Sub-Grupo';

/*==============================================================*/
/* Table: DNREL_OBJP_OBJE                                       */
/*==============================================================*/
create table DNREL_OBJP_OBJE
(
   ID_RELPE             int not null auto_increment,
   ID_OBJ_P             int,
   ID_OBJ_E             int,
   primary key (ID_RELPE)
);

/*==============================================================*/
/* Table: DNREL_OBJP_PROY                                       */
/*==============================================================*/
create table DNREL_OBJP_PROY
(
   ID_RELPP             int not null auto_increment,
   ID_OBJ_PRINC         int,
   ID_PROYECTO          int,
   primary key (ID_RELPP)
);

/*==============================================================*/
/* Table: DNROL                                                 */
/*==============================================================*/
create table DNROL
(
   ROL_ID               int not null auto_increment,
   ROL_NOMBRE           char(80),
   ROL_ID_CARGO         int,
   primary key (ROL_ID)
);

/*==============================================================*/
/* Table: DNSUBGRUPO                                            */
/*==============================================================*/
create table DNSUBGRUPO
(
   SUB_ID               int(11) not null auto_increment comment 'Campo de ID de la Tabla',
   SUB_DATTIM           timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   SUB_USER             char(10) comment 'Usuario que Ingreso el Registro',
   SUB_MACPC            char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   SUB_CODIGO           char(10) not null comment 'Codigo del Sub-Grupo',
   SUB_CODGRU           char(10) comment 'Codigo del Grupo Asociado',
   SUB_DESCRI           char(60) comment 'Nombre del Sub-Grupo',
   SUB_ACTIVO           bool,
   primary key (SUB_ID)
);

alter table DNSUBGRUPO comment 'Tabla de SubGrupos';

/*==============================================================*/
/* Table: DNSUBSECTOR                                           */
/*==============================================================*/
create table DNSUBSECTOR
(
   SBS_EMPRESA          char(6) not null,
   SBS_ID               int(11) not null auto_increment comment 'Campo de ID de la Tabla',
   SBS_DATTIM           timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   SBS_USER             char(10) comment 'Usuario que Ingreso el Registro',
   SBS_MACPC            char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   SBS_CODIGO           char(10) not null,
   SBS_CODPAR           int(11) comment 'Campo de ID de la Tabla',
   SBS_NOMBRE           char(60),
   SBS_ACTIVO           bool,
   primary key (SBS_ID, SBS_CODIGO),
   key AK_SBS_CODIGO (SBS_ID, SBS_CODIGO)
);

/*==============================================================*/
/* Table: DNTAREAS                                              */
/*==============================================================*/
create table DNTAREAS
(
   TAR_ID               int not null auto_increment,
   TAR_NOMBRE           char(80),
   TAR_FECHA_I          date,
   TAR_FECHA_F          date,
   TAR_FECHA_R          date,
   TAR_ID_PERSON        int,
   TAR_ID_ACTIVI        int,
   primary key (TAR_ID)
);

/*==============================================================*/
/* Table: DNTAREAS_AUTOMATICAS                                  */
/*==============================================================*/
create table DNTAREAS_AUTOMATICAS
(
   TARE_ID              int not null auto_increment,
   TARE_ID_CATEG        int,
   TARE_NOMBRE          char(80),
   TARE_DESCRIP         char(160),
   TARE_ID_DEPART       int,
   TARE_TIEMPO_MAX      int,
   TARE_TIEMPO_MIN      int,
   primary key (TARE_ID)
);

/*==============================================================*/
/* Table: DNTIPCONTACTO                                         */
/*==============================================================*/
create table DNTIPCONTACTO
(
   TCON_EMPRESA         char(6),
   TCON_ID              int(11) not null auto_increment comment 'Campo de ID de la Tabla',
   TCON_DATTIM          timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   TCON_USER            char(10) comment 'Usuario que Ingreso el Registro',
   TCON_MACPC           char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   TCON_CODIGO          char(10) not null,
   TCON_NOMBRE          char(60),
   TCON_ACTIVO          bool,
   primary key (TCON_ID)
);

/*==============================================================*/
/* Index: DNTIPCONTACTO_INDEX_1                                 */
/*==============================================================*/
create unique index DNTIPCONTACTO_INDEX_1 on DNTIPCONTACTO
(
   TCON_CODIGO
);

/*==============================================================*/
/* Table: DNTIPIVA                                              */
/*==============================================================*/
create table DNTIPIVA
(
   TIVA_EMPRESA         char(6),
   TIVA_ID              int(11) not null auto_increment comment 'Campo de ID de la Tabla',
   TIVA_DATTIM          timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   TIVA_USER            char(10) comment 'Usuario que Ingreso el Registro',
   TIVA_MACPC           char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   TIVA_CODIGO          char(3) not null,
   TIVA_DESCRI          char(40),
   TIVA_VALVEN          numeric(5,2),
   TIVA_VALCOM          numeric(5,2),
   TIVA_FCHDSD          date,
   TIVA_ACTIVO          bool,
   primary key (TIVA_ID)
);

alter table DNTIPIVA comment 'Tabla de Tipo de IVA';

/*==============================================================*/
/* Index: DNTIPIVA_INDEX_1                                      */
/*==============================================================*/
create index DNTIPIVA_INDEX_1 on DNTIPIVA
(
   TIVA_CODIGO
);

/*==============================================================*/
/* Table: DNTIPOMAESTRO                                         */
/*==============================================================*/
create table DNTIPOMAESTRO
(
   TMA_EMPRESA          char(6),
   TMA_ID               int(11) not null auto_increment comment 'Campo de ID de la Tabla',
   TMA_DATTIM           timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   TMA_USER             char(10) comment 'Usuario que Ingreso el Registro',
   TMA_MACPC            char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   TMA_CODIGO           char(10) not null,
   TMA_CODMAE           char(10),
   TMA_NOMBRE           char(80),
   TMA_ACTIVO           bool,
   primary key (TMA_ID)
);

/*==============================================================*/
/* Index: DNTIPOMAESTRO_INDEX_1                                 */
/*==============================================================*/
create index DNTIPOMAESTRO_INDEX_1 on DNTIPOMAESTRO
(
   TMA_CODIGO,
   TMA_CODMAE
);

/*==============================================================*/
/* Table: DNUNDMEDIDA                                           */
/*==============================================================*/
create table DNUNDMEDIDA
(
   MED_EMPRESA          char(6),
   MED_ID               int(11) not null auto_increment comment 'Campo de ID de la Tabla',
   MED_DATTIM           timestamp default CURRENT_TIMESTAMP comment 'Campo de Fecha y Hora de Creacion del Registro',
   MED_USER             char(10) comment 'Usuario que Ingreso el Registro',
   MED_MACPC            char(20) comment 'Mac Address del Equipo que incluyo el Registro',
   MED_CODIGO           char(8) not null,
   MED_DESCRI           char(60),
   MED_CANUND           numeric(12,4),
   MED_PESO             numeric(8,3),
   MED_VOLUME           numeric(8,3),
   MED_SIGNO            char(1),
   MED_MULCXU           bool,
   MED_ACTIVO           bool,
   primary key (MED_ID)
);

/*==============================================================*/
/* Index: DNUNDMEDIDA_INDEX_1                                   */
/*==============================================================*/
create unique index DNUNDMEDIDA_INDEX_1 on DNUNDMEDIDA
(
   MED_CODIGO
);

/*==============================================================*/
/* Table: DNUSUARIOS                                            */
/*==============================================================*/
create table DNUSUARIOS
(
   OPE_EMPRESA          char(6) not null,
   OPE_NUMERO           int(11) not null auto_increment,
   OPE_DATTIM           timestamp,
   OPE_NOMBRE           char(50),
   OPE_CARGO            char(120),
   OPE_CLAVE            char(30),
   OPE_MAPTAB           char(5),
   OPE_MAPMNU           char(5),
   OPE_LUNES            bool,
   OPE_MARTES           bool,
   OPE_MIERCOLES        bool,
   OPE_JUEVES           bool,
   OPE_VIRNES           bool,
   OPE_SABADO           bool,
   OPE_DOMINGO          bool,
   OPE_LUNAIN           char(5),
   OPE_LUNAFI           char(5),
   OPE_LUNPIN           char(5),
   OPE_LUNPFIN          char(5),
   OPE_MARAIN           char(5),
   OPE_MARAFI           char(5),
   OPE_MARPIN           char(5),
   OPE_MARPFI           char(5),
   OPE_MIEAIN           char(5),
   OPE_MIEAFI           char(5),
   OPE_MIEPIN           char(5),
   OPE_MIEPFI           char(5),
   OPE_JUEAIN           char(5),
   OPE_JUEAFI           char(5),
   OPE_JUEPIN           char(5),
   OPE_JUEPFI           char(5),
   OPE_VIEAIN           char(5),
   OPE_VIEAFI           char(5),
   OPE_VIEPIN           char(5),
   OPE_VIEPFI           char(5),
   OPE_SABAIN           char(5),
   OPE_SABAFI           char(5),
   OPE_SABPIN           char(5),
   OPE_SABPFI           char(5),
   OPE_DOMAIN           char(5),
   OPE_DOMAFI           char(5),
   OPE_DOMPIN           char(5),
   OPE_DOMPFI           char(5),
   OPE_ACCWEB           bool,
   OPE_ACCANDROID       bool,
   OPE_ACTIVO           bool,
   OPE_FCHING           date,
   OPE_TIPO_WEB         int(11),
   OPE_TIPO_DESKTOP     int(11),
   OPE_TIPO_MOVIL       int(11),
   OPE_STATUS           char(20),
   OPE_CODVEN           char(20),
   OPE_CODZON           char(20),
   OPE_USUARIO          char(50) not null,
   OPE_CORREO           char(255),
   OPE_RUTAIMG          char(255),
   OPE_IMGPERFIL        char(255),
   OPE_USUDAT           char(2),
   primary key (OPE_NUMERO)
);

/*==============================================================*/
/* Index: DNUSUARIOS_INDEX_1                                    */
/*==============================================================*/
create unique index DNUSUARIOS_INDEX_1 on DNUSUARIOS
(
   OPE_USUARIO
);

/*==============================================================*/
/* Table: DNVENDEDOR                                            */
/*==============================================================*/
create table DNVENDEDOR
(
   VEN_EMPRESA          char(6),
   VEN_ID               int(11) not null auto_increment,
   VEN_DATTIM           timestamp default CURRENT_TIMESTAMP,
   VEN_USER             char(10),
   VEN_MACPC            char(20),
   VEN_CODIGO           char(8) not null,
   VEN_NOMBRE           char(160),
   VEN_TELEFONO         text,
   VEN_COMISION         int(3),
   VEN_ACTIVO           bool,
   primary key (VEN_ID)
);

/*==============================================================*/
/* Index: DNVENDEDOR_INDEX_1                                    */
/*==============================================================*/
create unique index DNVENDEDOR_INDEX_1 on DNVENDEDOR
(
   VEN_CODIGO
);

alter table DNACTIVIDAD add constraint FK_DNACTIVIDAD_DNOBJ_ESPECIFICO foreign key (ACTIV_ID_OBJ_ESP)
      references DNOBJ_ESPECIFICO (OBJE_ID) on delete restrict on update restrict;

alter table DNACTIVIDAD_ECO add constraint FK_DNACTIVIDAD_DNEMPRESAS foreign key (ACT_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNBARUNDMED add constraint FK_DNBARUNDMED_DNEMPRESAS foreign key (BAR_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNBARUNDMED add constraint FK_DNPRODUCTO_DNBARUNDMED foreign key (BAR_CODPRO)
      references DNPRODUCTO (PRO_CODIGO) on delete restrict on update cascade;

alter table DNBARUNDMED add constraint FK_DNUNDMEDIDA_DNBARUNDMED2 foreign key (BAR_CODUND)
      references DNUNDMEDIDA (MED_CODIGO) on delete restrict on update cascade;

alter table DNBITACORA add constraint FK_DNBITACORA_DNEMPRESAS foreign key (BIT_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete set null on update restrict;

alter table DNBITACORA add constraint FK_DNUSUARIOS_DNBITACORA foreign key (BIT_CODUSER)
      references DNUSUARIOS (OPE_NUMERO) on delete restrict on update cascade;

alter table DNCARGO add constraint FK_DNREL_RECUR_CARGO_DNPERSONAS foreign key (CAR_ID_PERSON)
      references DNPERSONAS (PERS_ID) on delete restrict on update restrict;

alter table DNCARRITO_COMPRA add constraint FK_DNCARRITO_COMPRA_DNEMPRESAS foreign key (CAC_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNCARRITO_COMPRA add constraint FK_DNCARRITO_COMPRA_DNPRODUTO foreign key (CAC_CODPRO)
      references DNPRODUCTO (PRO_CODIGO) on delete restrict on update restrict;

alter table DNCLASIFICACION add constraint FK_DNCLASIFICACION_DNEMPRESAS foreign key (CLA_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNCONDIPAGO add constraint FK_DNCONDIPAGO_DNEMPRESAS foreign key (CDI_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNCONTACTOS add constraint FK_DNCONTACTOS_DNEMPRESAS foreign key (CON_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNCONTACTOS add constraint FK_DNMAESTRO_DNCONTACTOS foreign key (CON_CODMAE)
      references DNMAESTRO (MAE_CODIGO) on delete restrict on update cascade;

alter table DNCONTACTOS add constraint FK_DNTIPCONTACTO_DNCONTACTOS foreign key (CON_CODTIPC)
      references DNTIPCONTACTO (TCON_CODIGO) on delete restrict on update cascade;

alter table DNDIRECCION add constraint FK_DNDIRECCION_DNEMPRESAS foreign key (DIR_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNDIRECCION add constraint FK_DNDIRECCION_DNESTADOS foreign key (DIR_CODEST)
      references DNESTADOS (EST_ID) on delete restrict on update cascade;

alter table DNDIRECCION add constraint FK_DNDIRECCION_DNPAIS foreign key (DIR_CODPAI)
      references DNPAIS (PAI_ID) on delete restrict on update cascade;

alter table DNDIRECCION add constraint FK_DNDIRECCION_DNPARROQUIA foreign key (DIR_CODPAR)
      references DNPARROQUIAS (PAR_ID) on delete restrict on update cascade;

alter table DNDIRECCION add constraint FK_DNDIRECCION_DNSUBLOCALIDAD foreign key (DIR_CODMUN)
      references DNMUNICIPIOS (MUN_ID) on delete restrict on update cascade;

alter table DNDIRECCION add constraint FK_DNSUBSECTOR_DNDIRECCION foreign key (SBS_ID, DIR_CODSBS)
      references DNSUBSECTOR (SBS_ID, SBS_CODIGO) on delete restrict on update cascade;

alter table DNDOCUMENTOS add constraint FK_DNDOCUMENTOS_DNEMPRESAS foreign key (DOC_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNENCABEZADO add constraint FK_DNCONDIPAGO_DNENCABEZADO foreign key (ENC_CONDIC)
      references DNCONDIPAGO (CDI_DESCRI) on delete restrict on update restrict;

alter table DNENCABEZADO add constraint FK_DNDOCUMENTOS_DNENCABEZADO foreign key (ENC_CODDOC)
      references DNDOCUMENTOS (DOC_CODIGO) on delete restrict on update cascade;

alter table DNENCABEZADO add constraint FK_DNENCABEZADO_DNEMPRESAS foreign key (ENC_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNENCABEZADO add constraint FK_DNENCABEZADO_DNMAESTRO foreign key (ENC_CODMAE)
      references DNMAESTRO (MAE_CODIGO) on delete restrict on update cascade;

alter table DNENCABEZADO add constraint FK_DNVENDEDOR_DNENCABEZADO foreign key (ENC_CODVEN)
      references DNVENDEDOR (VEN_CODIGO) on delete restrict on update restrict;

alter table DNESTADOS add constraint FK_DNESTADOS_DNEMPRESAS foreign key (EST_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNESTADOS add constraint FK_DNESTADOS_DNPAIS foreign key (EST_CODPAI)
      references DNPAIS (PAI_ID) on delete restrict on update cascade;

alter table DNINVENTARIO add constraint FK_DNDOCUMENTOS_DNINVENTARIO foreign key (INV_CODDOC)
      references DNDOCUMENTOS (DOC_CODIGO) on delete restrict on update cascade;

alter table DNINVENTARIO add constraint FK_DNENCABEZADO_DNINVENTARIO foreign key (INV_CODENC)
      references DNENCABEZADO (ENC_CODIGO) on delete restrict on update cascade;

alter table DNINVENTARIO add constraint FK_DNINVENTARIO_DNEMPRESAS foreign key (INV_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNINVENTARIO add constraint FK_DNINVENTARIO_DNUNDMEDIDA foreign key (INV_UNIDAD)
      references DNUNDMEDIDA (MED_CODIGO) on delete restrict on update restrict;

alter table DNINVENTARIO add constraint FK_DNPRODUCTO_DNINVENTARIO foreign key (INV_CODPRO)
      references DNPRODUCTO (PRO_CODIGO) on delete restrict on update cascade;

alter table DNMAESTRO add constraint FK_DNMAESTRO_DNACTIVDAD_ECO foreign key (MAE_CODACT)
      references DNACTIVIDAD_ECO (ACT_CODIGO) on delete restrict on update set null;

alter table DNMAESTRO add constraint FK_DNMAESTRO_DNCONDIPAGO foreign key (MAE_CONDIC)
      references DNCONDIPAGO (CDI_DESCRI) on delete restrict on update restrict;

alter table DNMAESTRO add constraint FK_DNMAESTRO_DNDIRECCION foreign key (MAE_CODDIR)
      references DNDIRECCION (DIR_CODIGO) on delete restrict on update set null;

alter table DNMAESTRO add constraint FK_DNMAESTRO_DNEMPRESAS foreign key (MAE_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNMAESTRO add constraint FK_DNMAESTRO_DNLISTPRE foreign key (MAE_CODLIS)
      references DNLISTPRE (LIS_CODIGO) on delete restrict on update restrict;

alter table DNMAESTRO add constraint FK_DNMAESTRO_DNMONEDA foreign key (MAE_CODMON)
      references DNMONEDA (MON_CODIGO) on delete restrict on update set null;

alter table DNMAESTRO add constraint FK_DNTIPOMAESTRO_DNMAESTRO2 foreign key (MAE_CODTIPM)
      references DNTIPOMAESTRO (TMA_CODIGO) on delete restrict on update set null;

alter table DNMENU add constraint FK_DNMENU_DNEMPRESAS foreign key (MEN_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

#alter table DNMENU add constraint FK_DNPERMISOLOGIA_DNMENU foreign key (MEN_ID)
#      references DNPERMISOLOGIA (MIS_MENU) on delete restrict on update cascade;

alter table DNMONEDA add constraint FK_DNMONEDA_DNEMPRESAS foreign key (MON_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNMUNICIPIOS add constraint FK_DNMUNICIPIOS_DNEMPRESAS foreign key (MUN_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNMUNICIPIOS add constraint FK_DNMUNICIPIOS_DNESTADOS foreign key (MUN_CODEST)
      references DNESTADOS (EST_ID) on delete restrict on update cascade;

alter table DNPAIS add constraint FK_DNPAIS_DNEMPRESAS foreign key (PAI_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNPARROQUIAS add constraint FK_DNPARROQUIAS_DNEMPRESAS foreign key (PAR_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNPARROQUIAS add constraint FK_DNPARROQUIAS_DNMUNICIPIOS foreign key (PAR_CODMUN)
      references DNMUNICIPIOS (MUN_ID) on delete restrict on update restrict;

alter table DNPERMIACCIONES add constraint FK_DNPERMIACCIONES_DNEMPRESAS foreign key (ACC_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNPERMIACCIONES add constraint FK_DNPERMIACCIONES_DNPERMISOLOGIA foreign key (ACC_ID)
      references DNPERMISOLOGIA (MIS_ID) on delete restrict on update cascade;

#alter table DNPERMIACCIONES add constraint FK_DNPERMIACCIONES_DNPERMISO_USUARIOS foreign key (ACC_PERMISO)
#      references DNPERMISO_USUARIOS (PERUS_ID) on delete restrict on update restrict;

alter table DNPERMIACCIONES add constraint FK_DNPERMISO_GRUPAL_DNPERMIACCIONES foreign key (ACC_PERMISO)
      references DNPERMISO_GRUPAL (PER_ID) on delete cascade on update cascade;

alter table DNPERMISOLOGIA add constraint FK_DNPERMISOLOGIA_DNEMPRESAS foreign key (MIS_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNPERMISOLOGIA add constraint FK_DNPERMISOLOGIA_DNPERMISO_USUARIOS foreign key (MIS_PERMISO)
      references DNPERMISO_USUARIOS (PERUS_ID) on delete restrict on update restrict;

alter table DNPERMISOLOGIA add constraint FK_DNPERMISO_GRUPAL_DNPERMISOLOGIA foreign key (MIS_PERMISO)
      references DNPERMISO_GRUPAL (PER_ID) on delete cascade on update cascade;

alter table DNPERMISO_GRUPAL add constraint FK_DNPERMISO_GRUPAL_DNEMPRESAS foreign key (PER_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNPERMISO_USUARIOS add constraint FK_DNPERMISO_USUARIOS_DNEMPRESAS foreign key (PERUS_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNPERSONAS add constraint FK_DNPERSONAS_DNDEPARTAMENTO foreign key (PERS_ID_DEPART)
      references DNDEPARTAMENTO (DEP_ID) on delete restrict on update restrict;

alter table DNPRECIO add constraint FK_DNPRECIO_DNEMPRESAS foreign key (PRE_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNPRECIO add constraint FK_DNPRECIO_DNLISTPRE foreign key (PRE_CODLIS)
      references DNLISTPRE (LIS_CODIGO) on delete restrict on update cascade;

alter table DNPRECIO add constraint FK_DNPRODUCTO_DNPRECIO foreign key (PRE_CODPRO)
      references DNPRODUCTO (PRO_CODIGO) on delete restrict on update cascade;

alter table DNPRODUCTO add constraint FK_DNPRODUCTO_DNEMPRESAS foreign key (PRO_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNPRODUCTO add constraint FK_DNPRODUCTO_DNTIPIVA foreign key (PRO_TIPIVA)
      references DNTIPIVA (TIVA_CODIGO) on delete restrict on update cascade;

alter table DNPROYECTOS add constraint FK_DNPROYECTOS_DNCATEGORIAS_PROYECTOS foreign key (PROY_ID_CATEG)
      references DNCATEGORIAS_PROYECTOS (CAT_ID) on delete restrict on update restrict;

alter table DNPROYECTOS add constraint FK_DNPROYECTOS_DNMAESTRO foreign key (PROY_CODMAE)
      references DNMAESTRO (MAE_CODIGO) on delete restrict on update restrict;

alter table DNRELPROSUB add constraint FK_DNRELPROSUB_DNPRODUCTO foreign key (REL_CODPRO)
      references DNPRODUCTO (PRO_CODIGO) on delete restrict on update restrict;

#alter table DNRELPROSUB add constraint FK_DNRELPROSUB_DNSUBGRUPO foreign key (REL_CODSUB)
#      references DNSUBGRUPO (SUB_CODIGO) on delete restrict on update cascade;

alter table DNREL_OBJP_OBJE add constraint FK_DNREL_OBJP_OBJE_DNOBJ_ESPECIFICO foreign key (ID_OBJ_E)
      references DNOBJ_ESPECIFICO (OBJE_ID) on delete restrict on update restrict;

alter table DNREL_OBJP_OBJE add constraint FK_DNREL_OBJP_OBJE_DNOBJ_PRINCIPAL foreign key (ID_OBJ_P)
      references DNOBJ_PRINCIPAL (OBJP_ID) on delete restrict on update restrict;

alter table DNREL_OBJP_PROY add constraint FK_DNREL_OBJP_PROY_DNOBJ_PRINCIPAL foreign key (ID_OBJ_PRINC)
      references DNOBJ_PRINCIPAL (OBJP_ID) on delete restrict on update restrict;

alter table DNREL_OBJP_PROY add constraint FK_DNREL_OBJP_PROY_DNPROYECTOS foreign key (ID_PROYECTO)
      references DNPROYECTOS (PROY_ID) on delete restrict on update restrict;

alter table DNROL add constraint FK_DROL_DNCARGO foreign key (ROL_ID_CARGO)
      references DNCARGO (CAR_ID) on delete restrict on update restrict;

alter table DNSUBGRUPO add constraint FK_DNGRUPO_DNSUBGRUPO foreign key (SUB_CODGRU)
      references DNGRUPO (GRU_CODIGO) on delete restrict on update cascade;

alter table DNSUBSECTOR add constraint FK_DNSUBSECTOR_DNEMPRESAS foreign key (SBS_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNSUBSECTOR add constraint FK_DNSUBSECTOR_DNPARROQUIA foreign key (SBS_CODPAR)
      references DNPARROQUIAS (PAR_ID) on delete restrict on update cascade;

alter table DNTAREAS add constraint FK_DNTAREAS_DNACTIVIDAD foreign key (TAR_ID_ACTIVI)
      references DNACTIVIDAD (ACTIV_ID) on delete restrict on update restrict;

alter table DNTAREAS add constraint FK_DNTAREAS_DNPERSONAS foreign key (TAR_ID_PERSON)
      references DNPERSONAS (PERS_ID) on delete restrict on update restrict;

alter table DNTAREAS_AUTOMATICAS add constraint FK_DNTAREAS_AUTOMATICAS_DNCATEGORIAS_PROYECTOS foreign key (TARE_ID_CATEG)
      references DNCATEGORIAS_PROYECTOS (CAT_ID) on delete restrict on update restrict;

alter table DNTAREAS_AUTOMATICAS add constraint FK_DNTAREAS_AUTOMATICAS_DNDEPARTAMENTO foreign key (TARE_ID_DEPART)
      references DNDEPARTAMENTO (DEP_ID) on delete restrict on update restrict;

alter table DNTIPCONTACTO add constraint FK_DNTIPOCONTACTO_DNEMPRESAS foreign key (TCON_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNTIPIVA add constraint FK_DNTIPIVA_DNEMPRESAS foreign key (TIVA_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNTIPOMAESTRO add constraint FK_DNTIPOMAESTRO_DNEMPRESAS foreign key (TMA_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNTIPOMAESTRO add constraint FK_DNTIPOMAESTRO_DNMAESTRO foreign key (TMA_CODMAE)
      references DNMAESTRO (MAE_CODIGO) on delete restrict on update cascade;

alter table DNUNDMEDIDA add constraint FK_DNUNDMEDIDA_DNEMPRESAS foreign key (MED_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNUSUARIOS add constraint FK_DNPERMISO_GRUPAL_DNUSUARIOS foreign key (OPE_TIPO_WEB)
      references DNPERMISO_GRUPAL (PER_ID) on delete set null on update cascade;

alter table DNUSUARIOS add constraint FK_DNUSUARIOS_EMPRESAS foreign key (OPE_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

alter table DNVENDEDOR add constraint FK_DNVENDEROR_DNEMPRESAS foreign key (VEN_EMPRESA)
      references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

CREATE TRIGGER INCREMENTAL_DOC BEFORE INSERT ON DNENCABEZADO FOR EACH ROW BEGIN

  DECLARE nRegistro INT;
  DECLARE nNumIncre CHAR(10);

  SET nRegistro = (SELECT COUNT(*) FROM DNENCABEZADO WHERE ENC_CODIGO=NEW.ENC_CODIGO AND ENC_CODDOC=NEW.ENC_CODDOC);
  SET nNumIncre = (SELECT CONCAT(REPEAT('0',10-LENGTH(CONVERT(MAX(ENC_CODIGO)+1,CHAR(10)))),CONVERT(MAX(ENC_CODIGO)+1,CHAR(10))) AS ENC_CODIGO FROM DNENCABEZADO WHERE ENC_CODIGO=NEW.ENC_CODIGO AND ENC_CODDOC=NEW.ENC_CODDOC);
  
  IF (nRegistro=0) THEN
     SET NEW.ENC_CODIGO=NEW.ENC_CODIGO;
  ELSE
     SET NEW.ENC_CODIGO=nNumIncre;
  END IF;

END;;

CREATE TRIGGER INCREMENTAL_DETALLE BEFORE INSERT ON DNINVENTARIO FOR EACH ROW BEGIN

  DECLARE nNumIncre CHAR(10);

  SET nNumIncre = (SELECT ENC_CODIGO FROM DNENCABEZADO WHERE ENC_MACPC=NEW.INV_MACPC AND ENC_CODDOC=NEW.INV_CODDOC AND ENC_CODMAE=NEW.INV_CODMAE ORDER BY ENC_ID DESC LIMIT 1);
  
  SET NEW.INV_CODENC=nNumIncre;

END;;