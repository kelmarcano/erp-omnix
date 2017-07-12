/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     11/03/2015 08:28:47 p.m.                     */
/*==============================================================*/

drop table DNACTIVIDAD;
drop table DNACTIVIDAD_ECO;
drop table DNBARUNDMED;
drop table DNBITACORA;
drop table DNCARGO;
drop table DNCARRITO_COMPRA;
drop table DNCATEGORIAS_PROYECTOS;
drop table DNCLASIFICACION;
drop table DNCONDIPAGO;
drop table DNCONTACTOS;
drop table DNDEPARTAMENTO;
drop table DNDIRECCION;
drop table DNDOCUMENTOS;
drop table DNEMPRESAS;
drop table DNENCABEZADO;
drop table DNESTADOS;
drop table DNGRUPO;
drop table DNINVENTARIO;
drop table DNLISTPRE;
drop table DNMAESTRO;
drop table DNMENU;
drop table DNMONEDA;
drop table DNMUNICIPIOS;
drop table DNOBJ_ESPECIFICO;
drop table DNOBJ_PRINCIPAL;
drop table DNPAIS;
drop table DNPARROQUIAS;
drop table DNPERMIACCIONES;
drop table DNPERMISOLOGIA;
drop table DNPERMISO_GRUPAL;
drop table DNPERMISO_USUARIOS;
drop table DNPERSONAS;
drop table DNPRECIO;
drop table DNPRODUCTO;
drop table DNPROYECTOS;
drop table DNRELPROSUB;
drop table DNREL_OBJP_OBJE;
drop table DNREL_OBJP_PROY;
drop table DNROL;
drop table DNSUBGRUPO;
drop table DNSUBSECTOR;
drop table DNTAREAS;
drop table DNTAREAS_AUTOMATICAS;
drop table DNTIPCONTACTO;
drop table DNTIPIVA;
drop table DNTIPOMAESTRO;
drop table DNUNDMEDIDA;
drop table DNUSUARIOS;
drop table DNVENDEDOR;

/*==============================================================*/
/* Table: DNACTIVIDAD                                           */
/*==============================================================*/
create table DNACTIVIDAD (
   ACTIV_ID             SERIAL not null,
   ACTI_DESCRIP         CHAR(180)            null,
   ACTIV_FECHA_I        DATE                 null,
   ACTIV_FECHA_F        DATE                 null,
   ACTIV_ID_OBJ_ESP     INT4                 null,
   constraint PK_DNACTIVIDAD primary key (ACTIV_ID)
);

/*==============================================================*/
/* Table: DNACTIVIDAD_ECO                                       */
/*==============================================================*/
create table DNACTIVIDAD_ECO (
   ACT_EMPRESA          CHAR(6)              null,
   ACT_ID               SERIAL not null,
   ACT_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   ACT_USER             CHAR(10)             null,
   ACT_MACPC            CHAR(20)             null,
   ACT_CODIGO           CHAR(6)              not null,
   ACT_NOMBRE           CHAR(80)             null,
   ACT_ACTIVO           BOOL                 null,
   constraint PK_DNACTIVIDAD_ECO primary key (ACT_ID)
);

comment on table DNACTIVIDAD_ECO is 'Tabla de Actividad del Proveedor o Cliente';

comment on column DNACTIVIDAD_ECO.ACT_ID is 'Campo de ID de la Tabla';

comment on column DNACTIVIDAD_ECO.ACT_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNACTIVIDAD_ECO.ACT_USER is 'Usuario que Ingreso el Registro';

comment on column DNACTIVIDAD_ECO.ACT_MACPC is 'Mac Address del Equipo que incluyo el Registro';

/*==============================================================*/
/* Index: DNACTIVIDAD_INDEX_1                                   */
/*==============================================================*/
create unique index DNACTIVIDAD_INDEX_1 on DNACTIVIDAD_ECO (ACT_CODIGO);

/*==============================================================*/
/* Table: DNBARUNDMED                                           */
/*==============================================================*/
create table DNBARUNDMED (
   BAR_EMPRESA          CHAR(6)              null,
   BAR_ID               SERIAL not null,
   BAR_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   BAR_USER             CHAR(10)             null,
   BAR_MACPC            CHAR(20)             null,
   BAR_CODUND           CHAR(8)              null,
   BAR_CODPRO           CHAR(15)             null,
   BAR_BARRA            CHAR(15)             not null,
   constraint PK_DNBARUNDMED primary key (BAR_ID)
);

comment on column DNBARUNDMED.BAR_ID is 'Campo de ID de la Tabla';

comment on column DNBARUNDMED.BAR_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNBARUNDMED.BAR_USER is 'Usuario que Ingreso el Registro';

comment on column DNBARUNDMED.BAR_MACPC is 'Mac Address del Equipo que incluyo el Registro';

/*==============================================================*/
/* Table: DNBITACORA                                            */
/*==============================================================*/
create table DNBITACORA (
   BIT_EMPRESA          CHAR(6)              null,
   BIT_ID               SERIAL not null,
   BIT_MACPC            CHAR(20)             null,
   BIT_CODUSER          INT4                 null,
   BIT_USUARIO          CHAR(50)             null,
   BIT_DATE             DATE                 null,
   BIT_ACCION           CHAR(80)             null,
   BIT_DETALLES         CHAR(255)            null,
   BIT_NAVEGADOR        CHAR(18)             null,
   BIT_APPORG           CHAR(50)             null,
   constraint PK_DNBITACORA primary key (BIT_ID)
);

/*==============================================================*/
/* Index: DNBITACORA_INDEX_1                                    */
/*==============================================================*/
create  index DNBITACORA_INDEX_1 on DNBITACORA (BIT_USUARIO);

/*==============================================================*/
/* Table: DNCARGO                                               */
/*==============================================================*/
create table DNCARGO (
   CAR_ID               SERIAL not null,
   CAR_NOMBRE           CHAR(80)             null,
   CAR_ID_PERSON        INT4                 null,
   constraint PK_DNCARGO primary key (CAR_ID)
);

/*==============================================================*/
/* Table: DNCARRITO_COMPRA                                      */
/*==============================================================*/
create table DNCARRITO_COMPRA (
   CAC_EMPRESA          CHAR(6)              null,
   CAC_ID               SERIAL not null,
   CAC_MACPC            CHAR(20)             null,
   CAC_CODUSU           INT4                 null,
   CAC_CODPRO           CHAR(15)             null,
   CAN_ITEM             INT4                 null,
   CAN_FECHA            DATE                 null default 'CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP',
   constraint PK_DNCARRITO_COMPRA primary key (CAC_ID)
);

/*==============================================================*/
/* Table: DNCATEGORIAS_PROYECTOS                                */
/*==============================================================*/
create table DNCATEGORIAS_PROYECTOS (
   CAT_ID               SERIAL not null,
   CAT_ID_SUBCATE       INT4                 null,
   CAT_NOMBRE           CHAR(80)             null,
   CAT_DESCRIP          CHAR(160)            null,
   CAT_FECHA            DATE                 null default 'CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP',
   constraint PK_DNCATEGORIAS_PROYECTOS primary key (CAT_ID)
);

/*==============================================================*/
/* Table: DNCLASIFICACION                                       */
/*==============================================================*/
create table DNCLASIFICACION (
   CLA_EMPRESA          CHAR(6)              null,
   CLA_ID               INT4                 not null,
   SUB_CLA_ID           INT4                 null,
   CLA_NOMBRE           CHAR(80)             null,
   CLA_ORDEN            INT4                 null,
   constraint PK_DNCLASIFICACION primary key (CLA_ID)
);

/*==============================================================*/
/* Table: DNCONDIPAGO                                           */
/*==============================================================*/
create table DNCONDIPAGO (
   CDI_EMPRESA          CHAR(6)              null,
   CDI_ID               SERIAL not null,
   CDI_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   CDI_USER             CHAR(10)             null,
   CDI_MACPC            CHAR(20)             null,
   CDI_DESCRI           CHAR(60)             not null,
   CDI_ACTIVO           BOOL                 null,
   constraint PK_DNCONDIPAGO primary key (CDI_ID, CDI_DESCRI)
);

comment on column DNCONDIPAGO.CDI_ID is 'Campo de ID de la Tabla';

comment on column DNCONDIPAGO.CDI_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNCONDIPAGO.CDI_USER is 'Usuario que Ingreso el Registro';

comment on column DNCONDIPAGO.CDI_MACPC is 'Mac Address del Equipo que incluyo el Registro';

comment on column DNCONDIPAGO.CDI_DESCRI is 'Descripcion de la Condicion de Pago';

/*==============================================================*/
/* Index: CDI_DESCRI                                            */
/*==============================================================*/
create unique index CDI_DESCRI on DNCONDIPAGO (CDI_DESCRI);

/*==============================================================*/
/* Table: DNCONTACTOS                                           */
/*==============================================================*/
create table DNCONTACTOS (
   CON_EMPRESA          CHAR(6)              null,
   CON_ID               INT4                 not null,
   CON_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   CON_USER             CHAR(10)             null,
   CON_MACPC            CHAR(20)             null,
   CON_CODIGO           CHAR(10)             not null,
   CON_CODTIPC          CHAR(10)             not null,
   CON_CODMAE           CHAR(10)             not null,
   CON_CONTAC           CHAR(60)             null,
   CON_ACTIVO           BOOL                 null,
   constraint PK_DNCONTACTOS primary key (CON_ID)
);

comment on table DNCONTACTOS is 'Tabla de Contactos';

comment on column DNCONTACTOS.CON_ID is 'Campo de ID de la Tabla';

comment on column DNCONTACTOS.CON_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNCONTACTOS.CON_USER is 'Usuario que Ingreso el Registro';

comment on column DNCONTACTOS.CON_MACPC is 'Mac Address del Equipo que incluyo el Registro';

/*==============================================================*/
/* Index: DNCONTACTOS_INDEX_1                                   */
/*==============================================================*/
create unique index DNCONTACTOS_INDEX_1 on DNCONTACTOS (CON_CODIGO);

/*==============================================================*/
/* Table: DNDEPARTAMENTO                                        */
/*==============================================================*/
create table DNDEPARTAMENTO (
   DEP_ID               SERIAL not null,
   DEP_NOMBRE           CHAR(80)             null,
   constraint PK_DNDEPARTAMENTO primary key (DEP_ID)
);

/*==============================================================*/
/* Table: DNDIRECCION                                           */
/*==============================================================*/
create table DNDIRECCION (
   DIR_EMPRESA          CHAR(6)              null,
   DIR_ID               SERIAL not null,
   DIR_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   DIR_USER             CHAR(10)             null,
   DIR_MACPC            CHAR(20)             null,
   DIR_CODIGO           CHAR(10)             not null,
   DIR_CODPAI           INT4                 null,
   DIR_CODEST           INT4                 null,
   DIR_CODMUN           INT4                 null,
   DIR_CODPAR           INT4                 null,
   SBS_ID               INT4                 null,
   DIR_CODSBS           CHAR(10)             null,
   DIR_DESCRI           CHAR(80)             null,
   DIR_ACTIVO           BOOL                 null,
   constraint PK_DNDIRECCION primary key (DIR_ID)
);

comment on table DNDIRECCION is 'Tabla de Direcciones';

comment on column DNDIRECCION.DIR_ID is 'Campo de ID de la Tabla';

comment on column DNDIRECCION.DIR_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNDIRECCION.DIR_USER is 'Usuario que Ingreso el Registro';

comment on column DNDIRECCION.DIR_MACPC is 'Mac Address del Equipo que incluyo el Registro';

comment on column DNDIRECCION.DIR_CODPAI is 'Campo de ID de la Tabla';

/*==============================================================*/
/* Index: DNDIRECCION_INDEX_1                                   */
/*==============================================================*/
create unique index DNDIRECCION_INDEX_1 on DNDIRECCION (DIR_CODIGO);

/*==============================================================*/
/* Table: DNDOCUMENTOS                                          */
/*==============================================================*/
create table DNDOCUMENTOS (
   DOC_EMPRESA          CHAR(6)              null,
   DOC_ID               SERIAL not null,
   DOC_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   DOC_USER             CHAR(10)             null,
   DOC_MACPC            CHAR(20)             null,
   DOC_CODIGO           CHAR(3)              not null,
   DOC_DESCRI           CHAR(80)             null,
   DOC_CXC              BOOL                 null,
   DOC_CXP              BOOL                 null,
   DOC_INVACT           CHAR(2)              null,
   DOC_LIBVTA           BOOL                 null,
   DOC_LIBCOM           BOOL                 null,
   DOC_PAGOS            BOOL                 null,
   DOC_IVA              BOOL                 null,
   DOC_RETISLR          BOOL                 null,
   DOC_RETIVA           BOOL                 null,
   DOC_FISICO           BOOL                 null,
   DOC_LOGICO           BOOL                 null,
   DOC_ACTIVO           BOOL                 null,
   constraint PK_DNDOCUMENTOS primary key (DOC_ID)
);

comment on column DNDOCUMENTOS.DOC_ID is 'Campo de ID de la Tabla';

comment on column DNDOCUMENTOS.DOC_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNDOCUMENTOS.DOC_USER is 'Usuario que Ingreso el Registro';

comment on column DNDOCUMENTOS.DOC_MACPC is 'Mac Address del Equipo que incluyo el Registro';

comment on column DNDOCUMENTOS.DOC_CODIGO is 'Codigo del Tipo de Documento';

comment on column DNDOCUMENTOS.DOC_DESCRI is 'Descripcion del Tipo de Documento';

comment on column DNDOCUMENTOS.DOC_CXC is 'Cuentas por Cobrar';

comment on column DNDOCUMENTOS.DOC_CXP is 'Cuentas por Pagar';

comment on column DNDOCUMENTOS.DOC_LIBVTA is 'Se muestra el Documento en el Libro de Vtas';

comment on column DNDOCUMENTOS.DOC_LIBCOM is 'Se muestra el Documento en el Libro de Compras';

comment on column DNDOCUMENTOS.DOC_PAGOS is 'Acepta pago el Documento';

comment on column DNDOCUMENTOS.DOC_IVA is 'Calcula el IVA el documento';

comment on column DNDOCUMENTOS.DOC_RETISLR is 'Aplica ISLR';

comment on column DNDOCUMENTOS.DOC_FISICO is 'Mueve el Inventario Fisico';

comment on column DNDOCUMENTOS.DOC_LOGICO is 'Mueve el Inventario Logico';

comment on column DNDOCUMENTOS.DOC_ACTIVO is 'Determina si el Documento esta Activo';

/*==============================================================*/
/* Index: DNDOCUMENTOS_INDEX_1                                  */
/*==============================================================*/
create unique index DNDOCUMENTOS_INDEX_1 on DNDOCUMENTOS (DOC_CODIGO);

/*==============================================================*/
/* Table: DNEMPRESAS                                            */
/*==============================================================*/
create table DNEMPRESAS (
   EMP_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   EMP_USER             CHAR(10)             null,
   EMP_MACPC            CHAR(20)             null,
   EMP_CODIGO           CHAR(6)              not null,
   EMP_NOMBRE           CHAR(255)            null,
   EMP_RIF              CHAR(12)             not null,
   EMP_DIRECCION        TEXT                 null,
   EMP_ACTIVO           BOOL                 null,
   constraint PK_DNEMPRESAS primary key (EMP_CODIGO, EMP_RIF)
);

comment on table DNEMPRESAS is 'Tablas para Multi-Empresas';

/*==============================================================*/
/* Index: DNEMPRESAS_PK                                         */
/*==============================================================*/
create unique index DNEMPRESAS_PK on DNEMPRESAS (EMP_CODIGO,EMP_RIF);

/*==============================================================*/
/* Table: DNENCABEZADO                                          */
/*==============================================================*/
create table DNENCABEZADO (
   ENC_EMPRESA          CHAR(6)              null,
   ENC_ID               SERIAL not null,
   ENC_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   ENC_USER             CHAR(10)             null,
   ENC_MACPC            CHAR(20)             null,
   ENC_CODIGO           CHAR(15)             not null,
   ENC_CODDOC           CHAR(3)              null,
   ENC_CODMAE           CHAR(10)             null,
   ENC_ACTIVO           BOOL                 null,
   ENC_NROFIS           CHAR(10)             null,
   ENC_BASE             NUMERIC(12,2)        null,
   ENC_IVA              NUMERIC(12,2)        null,
   ENC_MONTO            NUMERIC(12,2)        null,
   ENC_DCTO             NUMERIC(5,2)         null,
   ENC_CONDIC           CHAR(60)             null,
   ENC_CODVEN           CHAR(8)              null,
   ENC_DIAVEN           NUMERIC(3,0)         null,
   ENC_FECHA            DATE                 null,
   ENC_FCHDEC           DATE                 null,
   ENC_FCHVEN           DATE                 null,
   ENC_ZOXVEN           CHAR(10)             null,
   constraint PK_DNENCABEZADO primary key (ENC_ID)
);

comment on column DNENCABEZADO.ENC_ID is 'Campo de ID de la tabla';

comment on column DNENCABEZADO.ENC_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNENCABEZADO.ENC_USER is 'Usuario que Ingreso el Registro';

comment on column DNENCABEZADO.ENC_MACPC is 'Mac Address del Equipo que incluyo el Registro';

comment on column DNENCABEZADO.ENC_CODIGO is 'Numero del Documento';

comment on column DNENCABEZADO.ENC_CODDOC is 'Codigo del Tipo de Documento';

comment on column DNENCABEZADO.ENC_CODMAE is 'Codigo del Maestro (Cliente / Proveedor)';

comment on column DNENCABEZADO.ENC_ACTIVO is 'Estado del Documento 1 Activo 0 Inactivo';

comment on column DNENCABEZADO.ENC_NROFIS is 'Numero de Control Fiscal del Documento';

comment on column DNENCABEZADO.ENC_BASE is 'Monto Base del Documento';

comment on column DNENCABEZADO.ENC_IVA is 'Iva del Documento';

comment on column DNENCABEZADO.ENC_MONTO is 'Total del Documento';

comment on column DNENCABEZADO.ENC_DCTO is 'Descuento del Documento';

comment on column DNENCABEZADO.ENC_CONDIC is 'Condicion del Documento';

comment on column DNENCABEZADO.ENC_DIAVEN is 'Dias de Vencimiento del Documento';

comment on column DNENCABEZADO.ENC_FECHA is 'Fecha de Registro del Documento';

comment on column DNENCABEZADO.ENC_FCHDEC is 'Fecha de Declaracion del Documento';

comment on column DNENCABEZADO.ENC_FCHVEN is 'Fecha de Vencimiento del Documento';

/*==============================================================*/
/* Index: DNENCABEZADO_INDEX_1                                  */
/*==============================================================*/
create  index DNENCABEZADO_INDEX_1 on DNENCABEZADO (ENC_CODIGO);

/*==============================================================*/
/* Table: DNESTADOS                                             */
/*==============================================================*/
create table DNESTADOS (
   EST_EMPRESA          CHAR(6)              null,
   EST_ID               SERIAL not null,
   EST_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   EST_USER             CHAR(10)             null,
   EST_MACPC            CHAR(20)             null,
   EST_CODPAI           INT4                 null,
   EST_NOMBRE           CHAR(60)             null,
   EST_ACTIVO           BOOL                 null,
   constraint PK_DNESTADOS primary key (EST_ID)
);

comment on column DNESTADOS.EST_ID is 'Campo de ID de la Tabla';

comment on column DNESTADOS.EST_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNESTADOS.EST_USER is 'Usuario que Ingreso el Registro';

comment on column DNESTADOS.EST_MACPC is 'Mac Address del Equipo que incluyo el Registro';

/*==============================================================*/
/* Table: DNGRUPO                                               */
/*==============================================================*/
create table DNGRUPO (
   GRU_ID               SERIAL not null,
   GRU_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   GRU_USER             CHAR(10)             null,
   GRU_MACPC            CHAR(20)             null,
   GRU_CODIGO           CHAR(10)             not null,
   GRU_DESCRI           CHAR(60)             null,
   GRU_ACTIVO           BOOL                 null,
   constraint PK_DNGRUPO primary key (GRU_ID)
);

comment on table DNGRUPO is 'Tabla de Grupos';

comment on column DNGRUPO.GRU_ID is 'Campo de ID de la Tabla';

comment on column DNGRUPO.GRU_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNGRUPO.GRU_USER is 'Usuario que Ingreso el Registro';

comment on column DNGRUPO.GRU_MACPC is 'Mac Address del Equipo que incluyo el Registro';

comment on column DNGRUPO.GRU_CODIGO is 'Codigo del Grupo';

comment on column DNGRUPO.GRU_DESCRI is 'Nombre del Grupo';

/*==============================================================*/
/* Index: DNGRUPO_INDEX_1                                       */
/*==============================================================*/
create  index DNGRUPO_INDEX_1 on DNGRUPO (GRU_CODIGO);

/*==============================================================*/
/* Table: DNINVENTARIO                                          */
/*==============================================================*/
create table DNINVENTARIO (
   INV_EMPRESA          CHAR(6)              null,
   INV_ID               SERIAL not null,
   INV_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   INV_USER             CHAR(10)             null,
   INV_MACPC            CHAR(20)             null,
   INV_CODDOC           CHAR(3)              null,
   INV_CODPRO           CHAR(15)             null,
   INV_CODENC           CHAR(15)             null,
   INV_CODMAE           CHAR(10)             null,
   INV_UNIDAD           CHAR(8)              null,
   INV_CANUND           NUMERIC(12,2)        null,
   INV_LOGICO           NUMERIC(1,0)         null,
   INV_FISICO           NUMERIC(1,0)         null,
   INV_ITEM             CHAR(6)              null,
   INV_CANTID           NUMERIC(12,2)        null,
   INV_PRECIO           NUMERIC(12,2)        null,
   INV_TOTAL            NUMERIC(12,2)        null,
   INV_ACTIVO           BOOL                 null,
   constraint PK_DNINVENTARIO primary key (INV_ID)
);

comment on column DNINVENTARIO.INV_ID is 'Campo de ID de la Tabla';

comment on column DNINVENTARIO.INV_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNINVENTARIO.INV_USER is 'Usuario que Ingreso el Registro';

comment on column DNINVENTARIO.INV_MACPC is 'Mac Address del Equipo que incluyo el Registro';

comment on column DNINVENTARIO.INV_CODDOC is 'Codigo del Tipo de Documento';

comment on column DNINVENTARIO.INV_CODPRO is 'Codigo del Producto';

comment on column DNINVENTARIO.INV_CODENC is 'Numero del Documento (Encabezado)';

comment on column DNINVENTARIO.INV_CODMAE is 'Codigo del Maestro (Cliente / Proveedor)';

comment on column DNINVENTARIO.INV_UNIDAD is 'Codigo de la Unidad de Medida';

comment on column DNINVENTARIO.INV_LOGICO is 'Valor para determinar si es una entrada (1) o salida (-1)';

comment on column DNINVENTARIO.INV_FISICO is 'Valor para determinar si es una entrada (1) o salida (-1)';

comment on column DNINVENTARIO.INV_ITEM is 'Numero de Items del Documento';

comment on column DNINVENTARIO.INV_CANTID is 'Candida del Producto';

comment on column DNINVENTARIO.INV_PRECIO is 'Precio del Producto';

comment on column DNINVENTARIO.INV_TOTAL is 'Total del Producto (Cant x Prec)';

comment on column DNINVENTARIO.INV_ACTIVO is 'Determina si el Item esta Activo en el Documento.';

/*==============================================================*/
/* Table: DNLISTPRE                                             */
/*==============================================================*/
create table DNLISTPRE (
   LIS_ID               SERIAL not null,
   LIS_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   LIS_USER             CHAR(10)             null,
   LIS_MACPC            CHAR(20)             null,
   LIS_CODIGO           CHAR(8)              not null,
   LIS_DESCRI           CHAR(40)             null,
   LIS_ACTIVO           BOOL                 null,
   constraint PK_DNLISTPRE primary key (LIS_ID)
);

comment on column DNLISTPRE.LIS_ID is 'Campo de ID de la Tabla';

comment on column DNLISTPRE.LIS_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNLISTPRE.LIS_USER is 'Usuario que Ingreso el Registro';

comment on column DNLISTPRE.LIS_MACPC is 'Mac Address del Equipo que incluyo el Registro';

comment on column DNLISTPRE.LIS_CODIGO is 'Codigo de la Lista de Precios';

comment on column DNLISTPRE.LIS_DESCRI is 'Nombre de la Lista de Precio';

/*==============================================================*/
/* Index: DNLISTPRE_INDEX_1                                     */
/*==============================================================*/
create unique index DNLISTPRE_INDEX_1 on DNLISTPRE (LIS_CODIGO);

/*==============================================================*/
/* Table: DNMAESTRO                                             */
/*==============================================================*/
create table DNMAESTRO (
   MAE_EMPRESA          CHAR(6)              null,
   MAE_ID               SERIAL not null,
   MAE_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   MAE_USER             CHAR(10)             null,
   MAE_MACPC            CHAR(20)             null,
   MAE_CODIGO           CHAR(10)             not null,
   MAE_CODTIPM          CHAR(10)             null,
   MAE_CODMON           CHAR(6)              null,
   MAE_CODACT           CHAR(6)              null,
   MAE_CODDIR           CHAR(10)             null,
   MAE_NOMBRE           CHAR(120)            null,
   MAE_RAZON            CHAR(20)             null,
   MAE_LIMITE           NUMERIC(10,2)        null,
   MAE_DCTO             NUMERIC(5,2)         null,
   MAE_CONDIC           CHAR(60)             null,
   MAE_CODLIS           CHAR(8)              null,
   MAE_ACTIVO           BOOL                 null,
   MAE_DIAS             NUMERIC(3,0)         null,
   MAE_FECHA            DATE                 null,
   MAE_OBSERV           TEXT                 null,
   MAE_RIF              CHAR(12)             null,
   MAE_NIT              CHAR(12)             null,
   MAE_CONTRI           CHAR(2)              null,
   MAE_TIPPER           CHAR(1)              null,
   MAE_RESIDE           CHAR(2)              null,
   MAE_ZONCOM           CHAR(30)             null,
   MAE_OTRMON           CHAR(6)              null,
   MAE_CONTESP          CHAR(8)              null,
   MAE_RETIVA           INT4                 null,
   MAE_MTOVEN           NUMERIC(12,2)        null,
   MAE_DIAVEN           NUMERIC(3,0)         null,
   MAE_EMAIL            CHAR(120)            null,
   MAE_CLIENTE          BOOL                 null,
   MAE_PROVEED          BOOL                 null,
   MAE_CLIENPOS         BOOL                 null,
   MAE_FOTO             CHAR                 null,
   constraint PK_DNMAESTRO primary key (MAE_ID)
);

comment on table DNMAESTRO is 'Tabla de Maestros de Clientes y Proveedores';

comment on column DNMAESTRO.MAE_ID is 'Campo de ID de la Tabla';

comment on column DNMAESTRO.MAE_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNMAESTRO.MAE_USER is 'Usuario que Ingreso el Registro';

comment on column DNMAESTRO.MAE_MACPC is 'Mac Address del Equipo que incluyo el Registro';

comment on column DNMAESTRO.MAE_CODLIS is 'Codigo de la Lista de Precios';

/*==============================================================*/
/* Index: DNMAESTRO_INDEX_1                                     */
/*==============================================================*/
create unique index DNMAESTRO_INDEX_1 on DNMAESTRO (MAE_CODIGO);

/*==============================================================*/
/* Table: DNMENU                                                */
/*==============================================================*/
create table DNMENU (
   MEN_EMPRESA          CHAR(6)              not null,
   MEN_ID               SERIAL not null,
   SUB_MEN_ID           NUMERIC(11,0)        not null,
   MEN_NOMBRE           CHAR(50)             null,
   MEN_DESCRIPCION      CHAR(80)             null,
   MEN_URL              CHAR(200)            null,
   MEN_ORDEN            NUMERIC(11,0)        null,
   MEN_TIPO             NUMERIC(11,0)        not null,
   MEN_IMG              CHAR(100)            null,
   MEN_IMGMENU          CHAR(20)             null,
   MEN_FECHA            DATE                 null,
   MEN_HORA             TIME                 null,
   MEN_EXTERNO          CHAR(2)              null,
   MEN_URLAMIGABLE      TEXT                 null,
   MEN_ICON             VARCHAR(150)         null,
   MEN_ESTMENU          BOOL                 null,
   constraint PK_DNMENU primary key (MEN_ID)
);

/*==============================================================*/
/* Table: DNMONEDA                                              */
/*==============================================================*/
create table DNMONEDA (
   MON_EMPRESA          CHAR(6)              null,
   MON_ID               SERIAL not null,
   MON_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   MON_USER             CHAR(10)             null,
   MON_MACPC            CHAR(20)             null,
   MON_CODIGO           CHAR(6)              not null,
   MON_NOMBRE           CHAR(40)             null,
   MON_NOMENC           CHAR(6)              null,
   MON_VALOR            NUMERIC(8,2)         null,
   MON_FCHVIG           DATE                 null,
   MON_ACTIVO           BOOL                 null,
   constraint PK_DNMONEDA primary key (MON_ID)
);

comment on table DNMONEDA is 'Tabla de Monedas';

comment on column DNMONEDA.MON_ID is 'Campo de ID de la Tabla';

comment on column DNMONEDA.MON_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNMONEDA.MON_USER is 'Usuario que Ingreso el Registro';

comment on column DNMONEDA.MON_MACPC is 'Mac Address del Equipo que incluyo el Registro';

/*==============================================================*/
/* Index: DNMONEDA_INDEX_1                                      */
/*==============================================================*/
create unique index DNMONEDA_INDEX_1 on DNMONEDA (MON_CODIGO);

/*==============================================================*/
/* Table: DNMUNICIPIOS                                          */
/*==============================================================*/
create table DNMUNICIPIOS (
   MUN_EMPRESA          CHAR(6)              not null,
   MUN_ID               SERIAL not null,
   MUN_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   MUN_USER             CHAR(10)             null,
   MUN_MACPC            CHAR(20)             null,
   MUN_CODEST           INT4                 not null,
   MUN_NOMBRE           CHAR(60)             null,
   MUN_ACTIVO           BOOL                 null,
   constraint PK_DNMUNICIPIOS primary key (MUN_ID),
   constraint AK_MUN_CODIGO_DNMUNICI unique (MUN_ID)
);

comment on column DNMUNICIPIOS.MUN_ID is 'Campo de ID de la Tabla';

comment on column DNMUNICIPIOS.MUN_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNMUNICIPIOS.MUN_USER is 'Usuario que Ingreso el Registro';

comment on column DNMUNICIPIOS.MUN_MACPC is 'Mac Address del Equipo que incluyo el Registro';

/*==============================================================*/
/* Table: DNOBJ_ESPECIFICO                                      */
/*==============================================================*/
create table DNOBJ_ESPECIFICO (
   OBJE_ID              SERIAL not null,
   OBJE_ID_OBJ_PRI      INT4                 null,
   OBJE_NOMBRE          CHAR(80)             null,
   OBJE_DESCRIP         CHAR(160)            null,
   constraint PK_DNOBJ_ESPECIFICO primary key (OBJE_ID)
);

/*==============================================================*/
/* Table: DNOBJ_PRINCIPAL                                       */
/*==============================================================*/
create table DNOBJ_PRINCIPAL (
   OBJP_ID              INT4                 not null,
   OBJP_NOMBRE          CHAR(80)             null,
   OBJP_DESCRIP         CHAR(160)            null,
   OBJP_PROYECTO        INT4                 null,
   constraint PK_DNOBJ_PRINCIPAL primary key (OBJP_ID)
);

/*==============================================================*/
/* Table: DNPAIS                                                */
/*==============================================================*/
create table DNPAIS (
   PAI_EMPRESA          CHAR(6)              null,
   PAI_ID               SERIAL not null,
   PAI_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   PAI_USER             CHAR(10)             null,
   PAI_MACPC            CHAR(20)             null,
   PAI_NOMBRE           CHAR(60)             null,
   PAI_CONTIN           CHAR(30)             null,
   PAI_ABRPAI           CHAR(3)              null,
   PAI_ACTIVO           BOOL                 null,
   constraint PK_DNPAIS primary key (PAI_ID)
);

comment on column DNPAIS.PAI_ID is 'Campo de ID de la Tabla';

comment on column DNPAIS.PAI_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNPAIS.PAI_USER is 'Usuario que Ingreso el Registro';

comment on column DNPAIS.PAI_MACPC is 'Mac Address del Equipo que incluyo el Registro';

/*==============================================================*/
/* Table: DNPARROQUIAS                                          */
/*==============================================================*/
create table DNPARROQUIAS (
   PAR_EMPRESA          CHAR(6)              null,
   PAR_ID               SERIAL not null,
   PAR_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   PAR_USER             CHAR(10)             null,
   PAR_MACPC            CHAR(20)             null,
   PAR_NOMBRE           CHAR(60)             null,
   PAR_CODMUN           INT4                 null,
   PAR_ACTIVO           BOOL                 null,
   constraint PK_DNPARROQUIAS primary key (PAR_ID)
);

comment on column DNPARROQUIAS.PAR_ID is 'Campo de ID de la Tabla';

comment on column DNPARROQUIAS.PAR_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNPARROQUIAS.PAR_USER is 'Usuario que Ingreso el Registro';

comment on column DNPARROQUIAS.PAR_MACPC is 'Mac Address del Equipo que incluyo el Registro';

/*==============================================================*/
/* Table: DNPERMIACCIONES                                       */
/*==============================================================*/
create table DNPERMIACCIONES (
   ACC_EMPRESA          CHAR(6)              null,
   ACC_ID               CHAR(50)             not null,
   ACC_PERMISO          INT4                 null,
   ACC_INCLUIR          NUMERIC(1,0)         null,
   ACC_CONSULTAR        NUMERIC(1,0)         null,
   ACC_MODIFICAR        NUMERIC(1,0)         null,
   ACC_ELIMINAR         NUMERIC(1,0)         null,
   ACC_IMPRIMIR         NUMERIC(1,0)         null,
   ACC_EXTRA1           NUMERIC(1,0)         null,
   ACC_EXTRA2           NUMERIC(1,0)         null,
   constraint PK_DNPERMIACCIONES primary key (ACC_ID)
);

/*==============================================================*/
/* Table: DNPERMISOLOGIA                                        */
/*==============================================================*/
create table DNPERMISOLOGIA (
   MIS_EMPRESA          CHAR(6)              null,
   MIS_ID               CHAR(50)             not null,
   MIS_PERMISO          INT4                 null,
   MIS_MENU             INT4                 null,
   MIS_TIPOMENU         NUMERIC(11,0)        null,
   MIS_TIPOPER          NUMERIC(11,0)        null,
   MIS_ACTIVO           BOOL                 null,
   constraint PK_DNPERMISOLOGIA primary key (MIS_ID)
);

/*==============================================================*/
/* Table: DNPERMISO_GRUPAL                                      */
/*==============================================================*/
create table DNPERMISO_GRUPAL (
   PER_EMPRESA          CHAR(6)              null,
   PER_ID               SERIAL not null,
   PER_NOMBRE           CHAR(100)            null,
   PER_DESCRIPCION      CHAR(200)            null,
   PER_TIPO             CHAR(1)              null,
   PER_ACTIVO           BOOL                 null,
   PER_FECHA            DATE                 null,
   PER_HORA             TIME                 null,
   PER_TIPOMENU         CHAR(1)              null,
   constraint PK_DNPERMISO_GRUPAL primary key (PER_ID)
);

/*==============================================================*/
/* Table: DNPERMISO_USUARIOS                                    */
/*==============================================================*/
create table DNPERMISO_USUARIOS (
   PERUS_EMPRESA        CHAR(6)              null,
   PERUS_ID             SERIAL not null,
   PERUS_USUARIO        CHAR(20)             null,
   PERUS_NOMBRE         CHAR(100)            null,
   PERUS_DESCRIPCION    CHAR(200)            null,
   PERUS_TIPO           CHAR(1)              null,
   PERUS_ACTIVO         BOOL                 null,
   PERUS_FECHA          DATE                 null,
   PERUS_HORA           TIME                 null,
   PERUS_TIPOMENU       CHAR(1)              null,
   constraint PK_DNPERMISO_USUARIOS primary key (PERUS_ID)
);

/*==============================================================*/
/* Table: DNPERSONAS                                            */
/*==============================================================*/
create table DNPERSONAS (
   PERS_ID              SERIAL not null,
   PERS_ID_DEPART       INT4                 null,
   PERS_NOMBRE          CHAR(80)             null,
   PERS_APELLIDO        CHAR(80)             null,
   constraint PK_DNPERSONAS primary key (PERS_ID)
);

/*==============================================================*/
/* Table: DNPRECIO                                              */
/*==============================================================*/
create table DNPRECIO (
   PRE_EMPRESA          CHAR(6)              null,
   PRE_ID               SERIAL not null,
   PRE_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   PRE_USER             CHAR(10)             null,
   PRE_MACPC            CHAR(20)             null,
   PRE_CODIGO           CHAR(10)             not null,
   PRE_CODPRO           CHAR(15)             null,
   PRE_CODLIS           CHAR(8)              null,
   PRE_MONTO            NUMERIC(12,2)        null,
   PRE_CODUNA           CHAR(8)              null,
   PRE_FCHVEN           DATE                 null,
   PRE_ACTIVO           BOOL                 null,
   constraint PK_DNPRECIO primary key (PRE_ID)
);

comment on table DNPRECIO is 'Tabla de Precios del Producto';

comment on column DNPRECIO.PRE_ID is 'Campo de ID de la Tabla';

comment on column DNPRECIO.PRE_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNPRECIO.PRE_USER is 'Usuario que Ingreso el Registro';

comment on column DNPRECIO.PRE_MACPC is 'Mac Address del Equipo que incluyo el Registro';

/*==============================================================*/
/* Table: DNPRODUCTO                                            */
/*==============================================================*/
create table DNPRODUCTO (
   PRO_EMPRESA          CHAR(6)              null,
   PRO_ID               SERIAL not null,
   PRO_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   PRO_USER             CHAR(10)             null,
   PRO_MACPC            CHAR(20)             null,
   PRO_CODIGO           CHAR(15)             not null,
   PRO_DESCRI           CHAR(80)             null,
   PRO_UTILIZ           CHAR(3)              null,
   PRO_TIPIVA           CHAR(3)              null,
   PRO_RELSUB           CHAR(10)             null,
   PRO_ACTIVO           BOOL                 null,
   constraint PK_DNPRODUCTO primary key (PRO_ID)
);

comment on table DNPRODUCTO is 'Tabla de Productos';

comment on column DNPRODUCTO.PRO_ID is 'Campo de ID de la Tabla';

comment on column DNPRODUCTO.PRO_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNPRODUCTO.PRO_USER is 'Usuario que Ingreso el Registro';

comment on column DNPRODUCTO.PRO_MACPC is 'Mac Address del Equipo que incluyo el Registro';

comment on column DNPRODUCTO.PRO_CODIGO is 'Codigo del Producto';

comment on column DNPRODUCTO.PRO_DESCRI is 'Descripcion del Producto';

comment on column DNPRODUCTO.PRO_UTILIZ is 'Utilizacion del Producto (Compra, Ventas, Servicio, etc)';

comment on column DNPRODUCTO.PRO_RELSUB is 'Codigo de Relacion del Producto con el Sub-Grupo';

/*==============================================================*/
/* Index: DNPRODUCTO_INDEX_1                                    */
/*==============================================================*/
create  index DNPRODUCTO_INDEX_1 on DNPRODUCTO (PRO_CODIGO);

/*==============================================================*/
/* Table: DNPROYECTOS                                           */
/*==============================================================*/
create table DNPROYECTOS (
   PROY_ID              SERIAL not null,
   PROY_NOMBRE          CHAR(80)             null,
   PROY_DESCRI          CHAR(160)            null,
   PROY_FECHA_I         DATE                 null,
   PROY_FECHA_F         DATE                 null,
   PROY_ID_CATEG        INT4                 null,
   PROY_CODMAE          CHAR(10)             null,
   PROY_LOCALIZACION    TEXT                 null,
   constraint PK_DNPROYECTOS primary key (PROY_ID)
);

/*==============================================================*/
/* Table: DNRELPROSUB                                           */
/*==============================================================*/
create table DNRELPROSUB (
   REL_ID               SERIAL not null,
   REL_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   REL_USER             CHAR(10)             null,
   REL_MACPC            CHAR(20)             null,
   REL_CODIGO           CHAR(10)             not null,
   REL_CODPRO           CHAR(15)             not null,
   REL_CODSUB           CHAR(10)             null,
   constraint PK_DNRELPROSUB primary key (REL_ID, REL_CODIGO),
   constraint AK_REL_CODPRO_DNRELPRO unique (REL_CODPRO, REL_ID)
);

comment on table DNRELPROSUB is 'Codigo de la Relacion del Producto con el Sub-Grupo';

comment on column DNRELPROSUB.REL_ID is 'Campo de ID de la Tabla';

comment on column DNRELPROSUB.REL_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNRELPROSUB.REL_USER is 'Usuario que Ingreso el Registro';

comment on column DNRELPROSUB.REL_MACPC is 'Mac Address del Equipo que incluyo el Registro';

comment on column DNRELPROSUB.REL_CODPRO is 'Codigo del Producto';

comment on column DNRELPROSUB.REL_CODSUB is 'Codigo del Sub-Grupo';

/*==============================================================*/
/* Table: DNREL_OBJP_OBJE                                       */
/*==============================================================*/
create table DNREL_OBJP_OBJE (
   ID_RELPE             SERIAL not null,
   ID_OBJ_P             INT4                 null,
   ID_OBJ_E             INT4                 null,
   constraint PK_DNREL_OBJP_OBJE primary key (ID_RELPE)
);

/*==============================================================*/
/* Table: DNREL_OBJP_PROY                                       */
/*==============================================================*/
create table DNREL_OBJP_PROY (
   ID_RELPP             SERIAL not null,
   ID_OBJ_PRINC         INT4                 null,
   ID_PROYECTO          INT4                 null,
   constraint PK_DNREL_OBJP_PROY primary key (ID_RELPP)
);

/*==============================================================*/
/* Table: DNROL                                                 */
/*==============================================================*/
create table DNROL (
   ROL_ID               SERIAL not null,
   ROL_NOMBRE           CHAR(80)             null,
   ROL_ID_CARGO         INT4                 null,
   constraint PK_DNROL primary key (ROL_ID)
);

/*==============================================================*/
/* Table: DNSUBGRUPO                                            */
/*==============================================================*/
create table DNSUBGRUPO (
   SUB_ID               SERIAL not null,
   SUB_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   SUB_USER             CHAR(10)             null,
   SUB_MACPC            CHAR(20)             null,
   SUB_CODIGO           CHAR(10)             not null,
   SUB_CODGRU           CHAR(10)             null,
   SUB_DESCRI           CHAR(60)             null,
   SUB_ACTIVO           BOOL                 null,
   constraint PK_DNSUBGRUPO primary key (SUB_ID)
);

comment on table DNSUBGRUPO is 'Tabla de SubGrupos';

comment on column DNSUBGRUPO.SUB_ID is 'Campo de ID de la Tabla';

comment on column DNSUBGRUPO.SUB_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNSUBGRUPO.SUB_USER is 'Usuario que Ingreso el Registro';

comment on column DNSUBGRUPO.SUB_MACPC is 'Mac Address del Equipo que incluyo el Registro';

comment on column DNSUBGRUPO.SUB_CODIGO is 'Codigo del Sub-Grupo';

comment on column DNSUBGRUPO.SUB_CODGRU is 'Codigo del Grupo Asociado';

comment on column DNSUBGRUPO.SUB_DESCRI is 'Nombre del Sub-Grupo';

/*==============================================================*/
/* Table: DNSUBSECTOR                                           */
/*==============================================================*/
create table DNSUBSECTOR (
   SBS_EMPRESA          CHAR(6)              not null,
   SBS_ID               SERIAL not null,
   SBS_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   SBS_USER             CHAR(10)             null,
   SBS_MACPC            CHAR(20)             null,
   SBS_CODIGO           CHAR(10)             not null,
   SBS_CODPAR           INT4                 null,
   SBS_NOMBRE           CHAR(60)             null,
   SBS_ACTIVO           BOOL                 null,
   constraint PK_DNSUBSECTOR primary key (SBS_ID, SBS_CODIGO),
   constraint AK_SBS_CODIGO_DNSUBSEC unique (SBS_ID, SBS_CODIGO)
);

comment on column DNSUBSECTOR.SBS_ID is 'Campo de ID de la Tabla';

comment on column DNSUBSECTOR.SBS_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNSUBSECTOR.SBS_USER is 'Usuario que Ingreso el Registro';

comment on column DNSUBSECTOR.SBS_MACPC is 'Mac Address del Equipo que incluyo el Registro';

comment on column DNSUBSECTOR.SBS_CODPAR is 'Campo de ID de la Tabla';

/*==============================================================*/
/* Table: DNTAREAS                                              */
/*==============================================================*/
create table DNTAREAS (
   TAR_ID               SERIAL not null,
   TAR_NOMBRE           CHAR(80)             null,
   TAR_FECHA_I          DATE                 null,
   TAR_FECHA_F          DATE                 null,
   TAR_FECHA_R          DATE                 null,
   TAR_ID_PERSON        INT4                 null,
   TAR_ID_ACTIVI        INT4                 null,
   constraint PK_DNTAREAS primary key (TAR_ID)
);

/*==============================================================*/
/* Table: DNTAREAS_AUTOMATICAS                                  */
/*==============================================================*/
create table DNTAREAS_AUTOMATICAS (
   TARE_ID              SERIAL not null,
   TARE_ID_CATEG        INT4                 null,
   TARE_NOMBRE          CHAR(80)             null,
   TARE_DESCRIP         CHAR(160)            null,
   TARE_ID_DEPART       INT4                 null,
   TARE_TIEMPO_MAX      INT4                 null,
   TARE_TIEMPO_MIN      INT4                 null,
   constraint PK_DNTAREAS_AUTOMATICAS primary key (TARE_ID)
);

/*==============================================================*/
/* Table: DNTIPCONTACTO                                         */
/*==============================================================*/
create table DNTIPCONTACTO (
   TCON_EMPRESA         CHAR(6)              null,
   TCON_ID              SERIAL not null,
   TCON_DATTIM          DATE                 null default CURRENT_TIMESTAMP,
   TCON_USER            CHAR(10)             null,
   TCON_MACPC           CHAR(20)             null,
   TCON_CODIGO          CHAR(10)             not null,
   TCON_DESCRI          CHAR(60)             null,
   TCON_ACTIVO          BOOL                 null,
   constraint PK_DNTIPCONTACTO primary key (TCON_ID)
);

comment on column DNTIPCONTACTO.TCON_ID is 'Campo de ID de la Tabla';

comment on column DNTIPCONTACTO.TCON_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNTIPCONTACTO.TCON_USER is 'Usuario que Ingreso el Registro';

comment on column DNTIPCONTACTO.TCON_MACPC is 'Mac Address del Equipo que incluyo el Registro';

/*==============================================================*/
/* Index: DNTIPCONTACTO_INDEX_1                                 */
/*==============================================================*/
create unique index DNTIPCONTACTO_INDEX_1 on DNTIPCONTACTO (TCON_CODIGO);

/*==============================================================*/
/* Table: DNTIPIVA                                              */
/*==============================================================*/
create table DNTIPIVA (
   TIVA_EMPRESA         CHAR(6)              null,
   TIVA_ID              SERIAL not null,
   TIVA_DATTIM          DATE                 null default CURRENT_TIMESTAMP,
   TIVA_USER            CHAR(10)             null,
   TIVA_MACPC           CHAR(20)             null,
   TIVA_CODIGO          CHAR(3)              not null,
   TIVA_DESCRI          CHAR(40)             null,
   TIVA_VALVEN          NUMERIC(5,2)         null,
   TIVA_VALCOM          NUMERIC(5,2)         null,
   TIVA_FCHDSD          DATE                 null,
   TIVA_ACTIVO          BOOL                 null,
   constraint PK_DNTIPIVA primary key (TIVA_ID)
);

comment on table DNTIPIVA is 'Tabla de Tipo de IVA';

comment on column DNTIPIVA.TIVA_ID is 'Campo de ID de la Tabla';

comment on column DNTIPIVA.TIVA_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNTIPIVA.TIVA_USER is 'Usuario que Ingreso el Registro';

comment on column DNTIPIVA.TIVA_MACPC is 'Mac Address del Equipo que incluyo el Registro';

/*==============================================================*/
/* Index: DNTIPIVA_INDEX_1                                      */
/*==============================================================*/
create  index DNTIPIVA_INDEX_1 on DNTIPIVA (TIVA_CODIGO);

/*==============================================================*/
/* Table: DNTIPOMAESTRO                                         */
/*==============================================================*/
create table DNTIPOMAESTRO (
   TMA_EMPRESA          CHAR(6)              null,
   TMA_ID               SERIAL not null,
   TMA_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   TMA_USER             CHAR(10)             null,
   TMA_MACPC            CHAR(20)             null,
   TMA_CODIGO           CHAR(10)             not null,
   TMA_CODMAE           CHAR(10)             null,
   TMA_DESCRI           CHAR(80)             null,
   TMA_ACTIVO           BOOL                 null,
   constraint PK_DNTIPOMAESTRO primary key (TMA_ID)
);

comment on column DNTIPOMAESTRO.TMA_ID is 'Campo de ID de la Tabla';

comment on column DNTIPOMAESTRO.TMA_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNTIPOMAESTRO.TMA_USER is 'Usuario que Ingreso el Registro';

comment on column DNTIPOMAESTRO.TMA_MACPC is 'Mac Address del Equipo que incluyo el Registro';

/*==============================================================*/
/* Index: DNTIPOMAESTRO_INDEX_1                                 */
/*==============================================================*/
create  index DNTIPOMAESTRO_INDEX_1 on DNTIPOMAESTRO (TMA_CODIGO,TMA_CODMAE);

/*==============================================================*/
/* Table: DNUNDMEDIDA                                           */
/*==============================================================*/
create table DNUNDMEDIDA (
   MED_EMPRESA          CHAR(6)              null,
   MED_ID               SERIAL not null,
   MED_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   MED_USER             CHAR(10)             null,
   MED_MACPC            CHAR(20)             null,
   MED_CODIGO           CHAR(8)              not null,
   MED_DESCRI           CHAR(60)             null,
   MED_CANUND           NUMERIC(12,4)        null,
   MED_PESO             NUMERIC(8,3)         null,
   MED_VOLUME           NUMERIC(8,3)         null,
   MED_SIGNO            CHAR(1)              null,
   MED_MULCXU           BOOL                 null,
   MED_ACTIVO           BOOL                 null,
   constraint PK_DNUNDMEDIDA primary key (MED_ID)
);

comment on column DNUNDMEDIDA.MED_ID is 'Campo de ID de la Tabla';

comment on column DNUNDMEDIDA.MED_DATTIM is 'Campo de Fecha y Hora de Creacion del Registro';

comment on column DNUNDMEDIDA.MED_USER is 'Usuario que Ingreso el Registro';

comment on column DNUNDMEDIDA.MED_MACPC is 'Mac Address del Equipo que incluyo el Registro';

/*==============================================================*/
/* Index: DNUNDMEDIDA_INDEX_1                                   */
/*==============================================================*/
create unique index DNUNDMEDIDA_INDEX_1 on DNUNDMEDIDA (MED_CODIGO);

/*==============================================================*/
/* Table: DNUSUARIOS                                            */
/*==============================================================*/
create table DNUSUARIOS (
   OPE_EMPRESA          CHAR(6)              not null,
   OPE_NUMERO           SERIAL not null,
   OPE_DATTIM           DATE                 null,
   OPE_NOMBRE           CHAR(50)             null,
   OPE_CARGO            CHAR(120)            null,
   OPE_CLAVE            CHAR(30)             null,
   OPE_MAPTAB           CHAR(5)              null,
   OPE_MAPMNU           CHAR(5)              null,
   OPE_LUNES            BOOL                 null,
   OPE_MARTES           BOOL                 null,
   OPE_MIERCOLES        BOOL                 null,
   OPE_JUEVES           BOOL                 null,
   OPE_VIRNES           BOOL                 null,
   OPE_SABADO           BOOL                 null,
   OPE_DOMINGO          BOOL                 null,
   OPE_LUNAIN           CHAR(5)              null,
   OPE_LUNAFI           CHAR(5)              null,
   OPE_LUNPIN           CHAR(5)              null,
   OPE_LUNPFIN          CHAR(5)              null,
   OPE_MARAIN           CHAR(5)              null,
   OPE_MARAFI           CHAR(5)              null,
   OPE_MARPIN           CHAR(5)              null,
   OPE_MARPFI           CHAR(5)              null,
   OPE_MIEAIN           CHAR(5)              null,
   OPE_MIEAFI           CHAR(5)              null,
   OPE_MIEPIN           CHAR(5)              null,
   OPE_MIEPFI           CHAR(5)              null,
   OPE_JUEAIN           CHAR(5)              null,
   OPE_JUEAFI           CHAR(5)              null,
   OPE_JUEPIN           CHAR(5)              null,
   OPE_JUEPFI           CHAR(5)              null,
   OPE_VIEAIN           CHAR(5)              null,
   OPE_VIEAFI           CHAR(5)              null,
   OPE_VIEPIN           CHAR(5)              null,
   OPE_VIEPFI           CHAR(5)              null,
   OPE_SABAIN           CHAR(5)              null,
   OPE_SABAFI           CHAR(5)              null,
   OPE_SABPIN           CHAR(5)              null,
   OPE_SABPFI           CHAR(5)              null,
   OPE_DOMAIN           CHAR(5)              null,
   OPE_DOMAFI           CHAR(5)              null,
   OPE_DOMPIN           CHAR(5)              null,
   OPE_DOMPFI           CHAR(5)              null,
   OPE_ACCWEB           BOOL                 null,
   OPE_ACCANDROID       BOOL                 null,
   OPE_ACTIVO           BOOL                 null,
   OPE_FCHING           DATE                 null,
   OPE_TIPO_WEB         INT4                 null,
   OPE_TIPO_DESKTOP     INT4                 null,
   OPE_TIPO_MOVIL       INT4                 null,
   OPE_STATUS           CHAR(20)             null,
   OPE_CODVEN           CHAR(20)             null,
   OPE_CODZON           CHAR(20)             null,
   OPE_USUARIO          CHAR(50)             not null,
   OPE_CORREO           CHAR(255)            null,
   OPE_RUTAIMG          CHAR(255)            null,
   OPE_IMGPERFIL        CHAR(255)            null,
   OPE_USUDAT           CHAR(2)              null,
   constraint PK_DNUSUARIOS primary key (OPE_NUMERO)
);

/*==============================================================*/
/* Index: DNUSUARIOS_INDEX_1                                    */
/*==============================================================*/
create unique index DNUSUARIOS_INDEX_1 on DNUSUARIOS (OPE_USUARIO);

/*==============================================================*/
/* Table: DNVENDEDOR                                            */
/*==============================================================*/
create table DNVENDEDOR (
   VEN_EMPRESA          CHAR(6)              null,
   VEN_ID               SERIAL not null,
   VEN_DATTIM           DATE                 null default CURRENT_TIMESTAMP,
   VEN_USER             CHAR(10)             null,
   VEN_MACPC            CHAR(20)             null,
   VEN_CODIGO           CHAR(8)              not null,
   VEN_NOMBRE           CHAR(160)            null,
   VEN_TELEFONO         TEXT                 null,
   VEN_COMISION         INT4                 null,
   VEN_ACTIVO           BOOL                 null,
   constraint PK_DNVENDEDOR primary key (VEN_ID)
);

/*==============================================================*/
/* Index: DNVENDEDOR_INDEX_1                                    */
/*==============================================================*/
create unique index DNVENDEDOR_INDEX_1 on DNVENDEDOR (VEN_CODIGO);

alter table DNACTIVIDAD add constraint FK_DNACTIVI_DNACTIVID_DNOBJ_ES foreign key (ACTIV_ID_OBJ_ESP) references DNOBJ_ESPECIFICO (OBJE_ID) on delete restrict on update restrict;
alter table DNACTIVIDAD_ECO add constraint FK_DNACTIVI_DNACTIVID_DNEMPRES foreign key (ACT_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNBARUNDMED add constraint FK_DNBARUND_DNBARUNDM_DNEMPRES foreign key (BAR_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNBARUNDMED add constraint FK_DNBARUND_DNPRODUCT_DNPRODUC foreign key (BAR_CODPRO) references DNPRODUCTO (PRO_CODIGO) on delete restrict on update cascade;
alter table DNBARUNDMED add constraint FK_DNBARUND_DNUNDMEDI_DNUNDMED foreign key (BAR_CODUND) references DNUNDMEDIDA (MED_CODIGO) on delete restrict on update cascade;
alter table DNBITACORA add constraint FK_DNBITACO_DNBITACOR_DNEMPRES foreign key (BIT_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNBITACORA add constraint FK_DNBITACO_DNUSUARIO_DNUSUARI foreign key (BIT_CODUSER) references DNUSUARIOS (OPE_NUMERO) on delete restrict on update cascade;
alter table DNCARGO add constraint FK_DNCARGO_DNREL_REC_DNPERSON foreign key (CAR_ID_PERSON) references DNPERSONAS (PERS_ID) on delete restrict on update restrict;
alter table DNCARRITO_COMPRA add constraint FK_DNCARRIT_DNCARRITO_DNEMPRES foreign key (CAC_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNCARRITO_COMPRA add constraint FK_DNCARRIT_DNCARRITO_DNPRODUC foreign key (CAC_CODPRO) references DNPRODUCTO (PRO_CODIGO) on delete restrict on update restrict;
alter table DNCLASIFICACION add constraint FK_DNCLASIF_DNCLASIFI_DNEMPRES foreign key (CLA_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNCONDIPAGO add constraint FK_DNCONDIP_DNCONDIPA_DNEMPRES foreign key (CDI_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNCONTACTOS add constraint FK_DNCONTAC_DNCONTACT_DNEMPRES foreign key (CON_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNCONTACTOS add constraint FK_DNCONTAC_DNMAESTRO_DNMAESTR foreign key (CON_CODMAE) references DNMAESTRO (MAE_CODIGO) on delete restrict on update cascade;
alter table DNCONTACTOS add constraint FK_DNCONTAC_DNTIPCONT_DNTIPCON foreign key (CON_CODTIPC) references DNTIPCONTACTO (TCON_CODIGO) on delete restrict on update cascade;
alter table DNDIRECCION add constraint FK_DNDIRECC_DNDIRECCI_DNEMPRES foreign key (DIR_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNDIRECCION add constraint FK_DNDIRECC_DNDIRECCI_DNESTADO foreign key (DIR_CODEST) references DNESTADOS (EST_ID) on delete restrict on update cascade;
alter table DNDIRECCION add constraint FK_DNDIRECC_DNDIRECCI_DNPAIS foreign key (DIR_CODPAI) references DNPAIS (PAI_ID) on delete restrict on update cascade;
alter table DNDIRECCION add constraint FK_DNDIRECC_DNDIRECCI_DNPARROQ foreign key (DIR_CODPAR) references DNPARROQUIAS (PAR_ID) on delete restrict on update cascade;
alter table DNDIRECCION add constraint FK_DNDIRECC_DNDIRECCI_DNMUNICI foreign key (DIR_CODMUN) references DNMUNICIPIOS (MUN_ID) on delete restrict on update cascade;
alter table DNDIRECCION add constraint FK_DNDIRECC_DNSUBSECT_DNSUBSEC foreign key (SBS_ID, DIR_CODSBS) references DNSUBSECTOR (SBS_ID, SBS_CODIGO) on delete restrict on update cascade;
alter table DNDOCUMENTOS add constraint FK_DNDOCUME_DNDOCUMEN_DNEMPRES foreign key (DOC_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNENCABEZADO add constraint FK_DNENCABE_DNCONDIPA_DNCONDIP foreign key (ENC_CONDIC) references DNCONDIPAGO (CDI_DESCRI) on delete restrict on update restrict;
alter table DNENCABEZADO add constraint FK_DNENCABE_DNDOCUMEN_DNDOCUME foreign key (ENC_CODDOC) references DNDOCUMENTOS (DOC_CODIGO) on delete restrict on update cascade;
alter table DNENCABEZADO add constraint FK_DNENCABE_DNENCABEZ_DNEMPRES foreign key (ENC_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNENCABEZADO add constraint FK_DNENCABE_DNENCABEZ_DNMAESTR foreign key (ENC_CODMAE) references DNMAESTRO (MAE_CODIGO) on delete restrict on update cascade;
alter table DNENCABEZADO add constraint FK_DNENCABE_DNVENDEDO_DNVENDED foreign key (ENC_CODVEN) references DNVENDEDOR (VEN_CODIGO) on delete restrict on update restrict;
alter table DNESTADOS add constraint FK_DNESTADO_DNESTADOS_DNEMPRES foreign key (EST_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNESTADOS add constraint FK_DNESTADO_DNESTADOS_DNPAIS foreign key (EST_CODPAI) references DNPAIS (PAI_ID) on delete restrict on update cascade;
alter table DNINVENTARIO add constraint FK_DNINVENT_DNDOCUMEN_DNDOCUME foreign key (INV_CODDOC) references DNDOCUMENTOS (DOC_CODIGO) on delete restrict on update cascade;
alter table DNINVENTARIO add constraint FK_DNINVENT_DNENCABEZ_DNENCABE foreign key (INV_CODENC) references DNENCABEZADO (ENC_CODIGO) on delete restrict on update cascade;
alter table DNINVENTARIO add constraint FK_DNINVENT_DNINVENTA_DNEMPRES foreign key (INV_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNINVENTARIO add constraint FK_DNINVENT_DNINVENTA_DNUNDMED foreign key (INV_UNIDAD) references DNUNDMEDIDA (MED_CODIGO) on delete restrict on update restrict;
alter table DNINVENTARIO add constraint FK_DNINVENT_DNPRODUCT_DNPRODUC foreign key (INV_CODPRO) references DNPRODUCTO (PRO_CODIGO) on delete restrict on update cascade;
alter table DNMAESTRO add constraint FK_DNMAESTR_DNMAESTRO_DNACTIVI foreign key (MAE_CODACT) references DNACTIVIDAD_ECO (ACT_CODIGO) on delete restrict on update set null;
alter table DNMAESTRO add constraint FK_DNMAESTR_DNMAESTRO_DNCONDIP foreign key (MAE_CONDIC) references DNCONDIPAGO (CDI_DESCRI) on delete restrict on update restrict;
alter table DNMAESTRO add constraint FK_DNMAESTR_DNMAESTRO_DNDIRECC foreign key (MAE_CODDIR) references DNDIRECCION (DIR_CODIGO) on delete restrict on update set null;
alter table DNMAESTRO add constraint FK_DNMAESTR_DNMAESTRO_DNEMPRES foreign key (MAE_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNMAESTRO add constraint FK_DNMAESTR_DNMAESTRO_DNLISTPR foreign key (MAE_CODLIS) references DNLISTPRE (LIS_CODIGO) on delete restrict on update restrict;
alter table DNMAESTRO add constraint FK_DNMAESTR_DNMAESTRO_DNMONEDA foreign key (MAE_CODMON) references DNMONEDA (MON_CODIGO) on delete restrict on update set null;
alter table DNMAESTRO add constraint FK_DNMAESTR_DNTIPOMAE_DNTIPOMA foreign key (MAE_CODTIPM) references DNTIPOMAESTRO (TMA_CODIGO) on delete restrict on update set null;
alter table DNMENU add constraint FK_DNMENU_DNMENU_DN_DNEMPRES foreign key (MEN_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNMENU add constraint FK_DNMENU_DNPERMISO_DNPERMIS foreign key (MEN_ID) references DNPERMISOLOGIA (MIS_MENU) on delete restrict on update cascade;
alter table DNMONEDA add constraint FK_DNMONEDA_DNMONEDA__DNEMPRES foreign key (MON_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNMUNICIPIOS add constraint FK_DNMUNICI_DNMUNICIP_DNEMPRES foreign key (MUN_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNMUNICIPIOS add constraint FK_DNMUNICI_DNMUNICIP_DNESTADO foreign key (MUN_CODEST) references DNESTADOS (EST_ID) on delete restrict on update cascade;
alter table DNPAIS add constraint FK_DNPAIS_DNPAIS_DN_DNEMPRES foreign key (PAI_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNPARROQUIAS add constraint FK_DNPARROQ_DNPARROQU_DNEMPRES foreign key (PAR_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNPARROQUIAS add constraint FK_DNPARROQ_DNPARROQU_DNMUNICI foreign key (PAR_CODMUN) references DNMUNICIPIOS (MUN_ID) on delete restrict on update restrict;
alter table DNPERMIACCIONES add constraint FK_DNPERMIA_DNPERMIAC_DNEMPRES foreign key (ACC_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNPERMIACCIONES add constraint FK_DNPERMIA_DNPERMIAC_DNPERMIS2 foreign key (ACC_ID) references DNPERMISOLOGIA (MIS_ID) on delete restrict on update cascade;
alter table DNPERMIACCIONES add constraint FK_DNPERMIA_DNPERMIAC_DNPERMIS foreign key (ACC_PERMISO) references DNPERMISO_USUARIOS (PERUS_ID) on delete restrict on update restrict;
alter table DNPERMIACCIONES add constraint FK_DNPERMIA_DNPERMISO_DNPERMIS foreign key (ACC_PERMISO) references DNPERMISO_GRUPAL (PER_ID) on delete cascade on update cascade;
alter table DNPERMISOLOGIA add constraint FK_DNPERMIS_DNPERMISO_DNEMPRES foreign key (MIS_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNPERMISOLOGIA add constraint FK_DNPERMIS_DNPERMISO_DNPERMIS foreign key (MIS_PERMISO) references DNPERMISO_USUARIOS (PERUS_ID) on delete restrict on update restrict;
alter table DNPERMISOLOGIA add constraint FK_DNPERMIS_DNPERMISO_DNPERMIS2 foreign key (MIS_PERMISO) references DNPERMISO_GRUPAL (PER_ID) on delete cascade on update cascade;
alter table DNPERMISO_GRUPAL add constraint FK_DNPERMIS_DNPERMISO_DNEMPRES foreign key (PER_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNPERMISO_USUARIOS add constraint FK_DNPERMIS_DNPERMISO_DNEMPRES foreign key (PERUS_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNPERSONAS add constraint FK_DNPERSON_DNPERSONA_DNDEPART foreign key (PERS_ID_DEPART) references DNDEPARTAMENTO (DEP_ID) on delete restrict on update restrict;
alter table DNPRECIO add constraint FK_DNPRECIO_DNPRECIO__DNEMPRES foreign key (PRE_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNPRECIO add constraint FK_DNPRECIO_DNPRECIO__DNLISTPR foreign key (PRE_CODLIS) references DNLISTPRE (LIS_CODIGO) on delete restrict on update cascade;
alter table DNPRECIO add constraint FK_DNPRECIO_DNPRODUCT_DNPRODUC foreign key (PRE_CODPRO) references DNPRODUCTO (PRO_CODIGO) on delete restrict on update cascade;
alter table DNPRODUCTO add constraint FK_DNPRODUC_DNPRODUCT_DNEMPRES foreign key (PRO_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNPRODUCTO add constraint FK_DNPRODUC_DNPRODUCT_DNTIPIVA foreign key (PRO_TIPIVA) references DNTIPIVA (TIVA_CODIGO) on delete restrict on update cascade;
alter table DNPROYECTOS add constraint FK_DNPROYEC_DNPROYECT_DNCATEGO foreign key (PROY_ID_CATEG) references DNCATEGORIAS_PROYECTOS (CAT_ID) on delete restrict on update restrict;
alter table DNPROYECTOS add constraint FK_DNPROYEC_DNPROYECT_DNMAESTR foreign key (PROY_CODMAE) references DNMAESTRO (MAE_CODIGO) on delete restrict on update restrict;
alter table DNRELPROSUB add constraint FK_DNRELPRO_DNRELPROS_DNPRODUC foreign key (REL_CODPRO) references DNPRODUCTO (PRO_CODIGO) on delete restrict on update restrict;
alter table DNRELPROSUB add constraint FK_DNRELPRO_DNRELPROS_DNSUBGRU foreign key (REL_CODSUB) references DNSUBGRUPO (SUB_CODIGO) on delete restrict on update cascade;
alter table DNREL_OBJP_OBJE add constraint FK_DNREL_OB_DNREL_OBJ_DNOBJ_ES foreign key (ID_OBJ_E) references DNOBJ_ESPECIFICO (OBJE_ID) on delete restrict on update restrict;
alter table DNREL_OBJP_OBJE add constraint FK_DNREL_OB_DNREL_OBJ_DNOBJ_PR foreign key (ID_OBJ_P) references DNOBJ_PRINCIPAL (OBJP_ID) on delete restrict on update restrict;
alter table DNREL_OBJP_PROY add constraint FK_DNREL_OB_DNREL_OBJ_DNOBJ_PR foreign key (ID_OBJ_PRINC) references DNOBJ_PRINCIPAL (OBJP_ID) on delete restrict on update restrict;
alter table DNREL_OBJP_PROY add constraint FK_DNREL_OB_DNREL_OBJ_DNPROYEC foreign key (ID_PROYECTO) references DNPROYECTOS (PROY_ID) on delete restrict on update restrict;
alter table DNROL add constraint FK_DNROL_DROL_DNCA_DNCARGO foreign key (ROL_ID_CARGO) references DNCARGO (CAR_ID) on delete restrict on update restrict;
alter table DNSUBGRUPO add constraint FK_DNSUBGRU_DNGRUPO_D_DNGRUPO foreign key (SUB_CODGRU) references DNGRUPO (GRU_CODIGO) on delete restrict on update cascade;
alter table DNSUBSECTOR add constraint FK_DNSUBSEC_DNSUBSECT_DNEMPRES foreign key (SBS_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNSUBSECTOR add constraint FK_DNSUBSEC_DNSUBSECT_DNPARROQ foreign key (SBS_CODPAR) references DNPARROQUIAS (PAR_ID) on delete restrict on update cascade;
alter table DNTAREAS add constraint FK_DNTAREAS_DNTAREAS__DNACTIVI foreign key (TAR_ID_ACTIVI) references DNACTIVIDAD (ACTIV_ID) on delete restrict on update restrict;
alter table DNTAREAS add constraint FK_DNTAREAS_DNTAREAS__DNPERSON foreign key (TAR_ID_PERSON) references DNPERSONAS (PERS_ID) on delete restrict on update restrict;
alter table DNTAREAS_AUTOMATICAS add constraint FK_DNTAREAS_DNTAREAS__DNCATEGO foreign key (TARE_ID_CATEG) references DNCATEGORIAS_PROYECTOS (CAT_ID) on delete restrict on update restrict;
alter table DNTAREAS_AUTOMATICAS add constraint FK_DNTAREAS_DNTAREAS__DNDEPART foreign key (TARE_ID_DEPART) references DNDEPARTAMENTO (DEP_ID) on delete restrict on update restrict;
alter table DNTIPCONTACTO add constraint FK_DNTIPCON_DNTIPOCON_DNEMPRES foreign key (TCON_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNTIPIVA add constraint FK_DNTIPIVA_DNTIPIVA__DNEMPRES foreign key (TIVA_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNTIPOMAESTRO add constraint FK_DNTIPOMA_DNTIPOMAE_DNEMPRES foreign key (TMA_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNTIPOMAESTRO add constraint FK_DNTIPOMA_DNTIPOMAE_DNMAESTR foreign key (TMA_CODMAE) references DNMAESTRO (MAE_CODIGO) on delete restrict on update cascade;
alter table DNUNDMEDIDA add constraint FK_DNUNDMED_DNUNDMEDI_DNEMPRES foreign key (MED_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNUSUARIOS add constraint FK_DNUSUARI_DNPERMISO_DNPERMIS foreign key (OPE_TIPO_WEB) references DNPERMISO_GRUPAL (PER_ID) on delete set null on update cascade;
alter table DNUSUARIOS add constraint FK_DNUSUARI_DNUSUARIO_DNEMPRES foreign key (OPE_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;
alter table DNVENDEDOR add constraint FK_DNVENDED_DNVENDERO_DNEMPRES foreign key (VEN_EMPRESA) references DNEMPRESAS (EMP_CODIGO) on delete restrict on update restrict;

create or replace function INCREMENTAL_NUMDOC() returns trigger as $INCREMENTAL_NUMDOC$
  DECLARE
       nRegistro INT;
       nNumIncre CHAR(10);
  BEGIN
       nRegistro = (SELECT COUNT(*) FROM DNENCABEZADO WHERE ENC_CODIGO=NEW.ENC_CODIGO AND ENC_CODDOC=NEW.ENC_CODDOC);
       nNumIncre = (SELECT CONCAT(REPEAT('0',10-LENGTH((CAST(MAX(ENC_CODIGO) AS NUMERIC)+1)::TEXT)),(CAST(MAX(ENC_CODIGO) AS NUMERIC)+1)::TEXT) AS ENC_CODIGO FROM DNENCABEZADO WHERE ENC_CODIGO=NEW.ENC_CODIGO AND ENC_CODDOC=NEW.ENC_CODDOC);
  
       IF (nRegistro=0) THEN
           NEW.ENC_CODIGO=NEW.ENC_CODIGO;
       ELSE
           NEW.ENC_CODIGO=nNumIncre;
       END IF;

       RETURN NEW;
  END;
$INCREMENTAL_NUMDOC$ LANGUAGE plpgsql;

create or replace function INCREMENTAL_NUMDOC_DETALLE() returns trigger as $INCREMENTAL_NUMDOC_DETALLE$
  DECLARE
       nNumIncre CHAR(10);
  BEGIN
       nNumIncre = (SELECT ENC_CODIGO FROM DNENCABEZADO WHERE ENC_MACPC=NEW.INV_MACPC AND ENC_CODDOC=NEW.INV_CODDOC AND ENC_CODMAE=NEW.INV_CODMAE ORDER BY ENC_ID DESC LIMIT 1);
  
       NEW.INV_CODENC=nNumIncre;

       RETURN NEW;
  END;
$INCREMENTAL_NUMDOC_DETALLE$ LANGUAGE plpgsql;

CREATE TRIGGER INCREMENTAL_NUMDOC BEFORE INSERT 
    ON DNENCABEZADO FOR EACH ROW 
    EXECUTE PROCEDURE INCREMENTAL_NUMDOC();
    
CREATE TRIGGER INCREMENTAL_NUMDOC_DETALLE BEFORE INSERT 
    ON DNINVENTARIO FOR EACH ROW 
    EXECUTE PROCEDURE INCREMENTAL_NUMDOC_DETALLE();