SELECT INV_CODDOC,INV_CODPRO,INV_CANTID,INV_PRECIO,INV_NUMDOC
FROM DNINVENTARIO
WHERE INV_NUMDOC = $P{numfact}