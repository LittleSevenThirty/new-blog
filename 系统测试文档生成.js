const { Document, Packer, Paragraph, TextRun, Table, TableRow, TableCell, AlignmentType, WidthType, BorderStyle, HeadingLevel } = require('docx');
const fs = require('fs');

const doc = new Document({
  sections: [{
    properties: {
      page: {
        size: {
          width: 12240,  // US Letter
          height: 15840
        },
        margin: { top: 1440, right: 1440, bottom: 1440, left: 1440 }
      }
    },
    children: [
      // 标题
      new Paragraph({
        heading: HeadingLevel.HEADING_1,
        children: [new TextRun({ text: '七、系统测试', bold: true, size: 28 })]
      }),
      
      // 测试范围
      new Paragraph({
        heading: HeadingLevel.HEADING_2,
        children: [new TextRun({ text: '（一）测试范围', bold: true, size: 24 })]
      }),
      
      // 表7.1 用户登录注册测试范围表
      new Paragraph({ children: [new TextRun({ text: '表7.1 用户登录注册测试范围表', bold: true, size: 20 })] }),
      new Table({
        width: { size: 9360, type: WidthType.DXA },
        columnWidths: [3120, 3120, 3120],
        rows: [
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun({ text: '功能点', bold: true })], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun({ text: '测试类型', bold: true })], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun({ text: '测试方法', bold: true })], alignment: AlignmentType.CENTER })]
              })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('用户登录所属模块')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('功能测试、界面测试')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('黑盒测试')], alignment: AlignmentType.CENTER })]
              })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('用户注册所属模块')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('功能测试、界面测试')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('黑盒测试')], alignment: AlignmentType.CENTER })]
              })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('第三方登录所属模块')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('功能测试、界面测试')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('黑盒测试')], alignment: AlignmentType.CENTER })]
              })
            ]
          })
        ]
      }),
      
      new Paragraph({ children: [new TextRun(' ')] }),
      
      // 表7.2 用户模块测试范围表
      new Paragraph({ children: [new TextRun({ text: '表7.2 用户模块测试范围表', bold: true, size: 20 })] }),
      new Table({
        width: { size: 9360, type: WidthType.DXA },
        columnWidths: [3120, 3120, 3120],
        rows: [
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun({ text: '功能点', bold: true })], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun({ text: '测试类型', bold: true })], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun({ text: '测试方法', bold: true })], alignment: AlignmentType.CENTER })]
              })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('查看首页')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('功能测试、界面测试')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('黑盒测试')], alignment: AlignmentType.CENTER })]
              })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('查看文章详情页')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('功能测试、界面测试')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('黑盒测试')], alignment: AlignmentType.CENTER })]
              })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('查看分类页')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('功能测试、界面测试')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('黑盒测试')], alignment: AlignmentType.CENTER })]
              })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('查看标签页')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('功能测试、界面测试')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('黑盒测试')], alignment: AlignmentType.CENTER })]
              })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('查看树洞页')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('功能测试、界面测试')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('黑盒测试')], alignment: AlignmentType.CENTER })]
              })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('查看留言页')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('功能测试、界面测试')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('黑盒测试')], alignment: AlignmentType.CENTER })]
              })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('查看收藏夹页')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('功能测试、界面测试')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('黑盒测试')], alignment: AlignmentType.CENTER })]
              })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('修改个人信息')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('功能测试、界面测试')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('黑盒测试')], alignment: AlignmentType.CENTER })]
              })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('修改邮箱')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('功能测试、界面测试')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('黑盒测试')], alignment: AlignmentType.CENTER })]
              }
            ]
          }),
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('重置密码')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('功能测试、界面测试')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('黑盒测试')], alignment: AlignmentType.CENTER })]
              }
            ]
          })
        ]
      }),
      
      new Paragraph({ children: [new TextRun(' ')] }),
      
      // 表7.3 管理员模块测试范围表
      new Paragraph({ children: [new TextRun({ text: '表7.3 管理员模块测试范围表', bold: true, size: 20 })] }),
      new Table({
        width: { size: 9360, type: WidthType.DXA },
        columnWidths: [3120, 3120, 3120],
        rows: [
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun({ text: '功能点', bold: true })], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun({ text: '测试类型', bold: true })], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun({ text: '测试方法', bold: true })], alignment: AlignmentType.CENTER })]
              })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('查看仪表盘页')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('功能测试、界面测试')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('黑盒测试')], alignment: AlignmentType.CENTER })]
              }
            ]
          }),
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('查看用户管理页')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('功能测试、界面测试')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('黑盒测试')], alignment: AlignmentType.CENTER })]
              }
            ]
          }),
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('查看文章管理页')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('功能测试、界面测试')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('黑盒测试')], alignment: AlignmentType.CENTER })]
              }
            ]
          }),
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('查看评论管理页')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('功能测试、界面测试')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('黑盒测试')], alignment: AlignmentType.CENTER })]
              }
            ]
          }),
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('查看分类管理页')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('功能测试、界面测试')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('黑盒测试')], alignment: AlignmentType.CENTER })]
              }
            ]
          }),
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('查看标签管理页')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('功能测试、界面测试')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('黑盒测试')], alignment: AlignmentType.CENTER })]
              }
            ]
          }),
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('查看树洞管理页')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('功能测试、界面测试')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('黑盒测试')], alignment: AlignmentType.CENTER })]
              }
            ]
          }),
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('查看留言管理页')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('功能测试、界面测试')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('黑盒测试')], alignment: AlignmentType.CENTER })]
              }
            ]
          }),
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('查看轮播图管理页')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('功能测试、界面测试')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('黑盒测试')], alignment: AlignmentType.CENTER })]
              }
            ]
          }),
          new TableRow({
            children: [
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('查看网站信息管理页')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('功能测试、界面测试')], alignment: AlignmentType.CENTER })]
              }),
              new TableCell({
                width: { size: 3120, type: WidthType.DXA },
                borders: {
                  top: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  bottom: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  left: { style: BorderStyle.SINGLE, size: 1, color: '000000' },
                  right: { style: BorderStyle.SINGLE, size: 1, color: '000000' }
                },
                children: [new Paragraph({ children: [new TextRun('黑盒测试')], alignment: AlignmentType.CENTER })]
              }
            ]
          })
        ]
      }),
      
      new Paragraph({ children: [new TextRun(' ')] }),
      
      // 测试用例设计
      new Paragraph({
        heading: HeadingLevel.HEADING_2,
        children: [new TextRun({ text: '（二）测试用例设计', bold: true, size: 24 })]
      }),
      
      // 表7.