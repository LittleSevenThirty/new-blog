const { Document, Packer, Paragraph, TextRun, Table, TableRow, TableCell, AlignmentType, WidthType } = require('docx');
const fs = require('fs');

// 读取SQL文件
const sqlContent = fs.readFileSync('e:\\Github2\\毕业设计\\new-blog\\blog_backend\\littleseventhirty-blog\\docs\\sql\\create.sql', 'utf8');
console.log('SQL文件读取成功，长度:', sqlContent.length);

// 解析SQL文件，提取表结构
function parseSql(sqlContent) {
  const tables = [];
  
  // 分割SQL文件为多个语句
  const statements = sqlContent.split(';');
  
  console.log('开始解析SQL语句...');
  let count = 0;
  
  for (const statement of statements) {
    const trimmedStatement = statement.trim();
    if (trimmedStatement.startsWith('CREATE TABLE')) {
      count++;
      
      // 提取表名
      let tableNameMatch = trimmedStatement.match(/CREATE TABLE\s+`?([^`\s]+)`?/);
      if (!tableNameMatch) continue;
      const tableName = tableNameMatch[1];
      
      console.log(`找到表 ${count}: ${tableName}`);
      
      // 提取表内容
      let tableContentMatch = trimmedStatement.match(/\(([\s\S]*?)\)\s*(?:ENGINE|ROW_FORMAT)/);
      if (!tableContentMatch) continue;
      const tableContent = tableContentMatch[1];
      
      const fields = [];
      
      // 分割字段
      const fieldLines = tableContent.split(',');
      for (const line of fieldLines) {
        const trimmedLine = line.trim();
        if (trimmedLine === '' || trimmedLine.startsWith('PRIMARY KEY')) continue;
        
        // 提取字段信息
        const fieldMatch = trimmedLine.match(/`([^`]+)`\s+([^\s]+)(?:\([^)]+\))?\s*(NOT NULL)?(?:\s*DEFAULT\s*[^\s,]+)?\s*(?:COMMENT\s+'([^']+)')?/);
        if (fieldMatch) {
          const fieldName = fieldMatch[1];
          let fieldType = fieldMatch[2];
          const notNull = fieldMatch[3] ? '是' : '否';
          const comment = fieldMatch[4] || '';
          
          fields.push({
            name: fieldName,
            type: fieldType,
            notNull: notNull,
            comment: comment
          });
        }
      }
      
      console.log(`表 ${tableName} 有 ${fields.length} 个字段`);
      
      if (fields.length > 0) {
        tables.push({
          name: tableName,
          fields: fields
        });
      }
    }
  }
  
  console.log('解析完成，共找到', tables.length, '个表');
  return tables;
}

// 生成Word文档
function generateWord(tables) {
  console.log('开始生成Word文档，表数量:', tables.length);
  
  const doc = new Document({
    sections: [
      {
        properties: {},
        children: [
          new Paragraph({
            text: '数据库表结构',
            heading: 'Heading1',
            alignment: AlignmentType.CENTER,
            spacing: {
              before: 200,
              after: 200
            }
          }),
          ...tables.map((table, index) => {
            console.log(`添加表 ${index + 1}: ${table.name}`);
            return [
              new Paragraph({
                text: `表 ${index + 1} ${table.name}表`,
                heading: 'Heading2',
                spacing: {
                  before: 150,
                  after: 100
                }
              }),
              new Table({
                width: {
                  size: 100,
                  type: WidthType.PERCENTAGE
                },
                rows: [
                  new TableRow({
                    children: [
                      new TableCell({
                        children: [new Paragraph('字段名')],
                        width: { size: 20, type: WidthType.PERCENTAGE }
                      }),
                      new TableCell({
                        children: [new Paragraph('名称')],
                        width: { size: 20, type: WidthType.PERCENTAGE }
                      }),
                      new TableCell({
                        children: [new Paragraph('类型')],
                        width: { size: 20, type: WidthType.PERCENTAGE }
                      }),
                      new TableCell({
                        children: [new Paragraph('非空')],
                        width: { size: 20, type: WidthType.PERCENTAGE }
                      }),
                      new TableCell({
                        children: [new Paragraph('备注')],
                        width: { size: 20, type: WidthType.PERCENTAGE }
                      })
                    ]
                  }),
                  ...table.fields.map(field => {
                    return new TableRow({
                      children: [
                        new TableCell({
                          children: [new Paragraph(field.name)]
                        }),
                        new TableCell({
                          children: [new Paragraph(field.comment || field.name)]
                        }),
                        new TableCell({
                          children: [new Paragraph(field.type)]
                        }),
                        new TableCell({
                          children: [new Paragraph(field.notNull)]
                        }),
                        new TableCell({
                          children: [new Paragraph('')]
                        })
                      ]
                    });
                  })
                ]
              })
            ];
          }).flat()
        ]
      }
    ]
  });
  
  Packer.toBuffer(doc).then(buffer => {
    fs.writeFileSync('e:\\Github2\\毕业设计\\new-blog\\blog_documents\\database_tables.docx', buffer);
    console.log('Word文档生成成功！文件大小:', buffer.length, '字节');
  }).catch(error => {
    console.error('生成Word文档时出错:', error);
  });
}

// 执行解析和生成
const tables = parseSql(sqlContent);
generateWord(tables);
