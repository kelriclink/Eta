---
name: anima-prompt-gen
description: 将图片或文字描述转化为高质量Anima英文提示词。当用户需要生成Anima提示词、反推图片提示词、优化Anima模型描述或讨论AI绘画提示词工程时使用。本技能整合了完整的Anima提示词规则体系，无论使用何种LLM核心，请始终遵循本规则输出。
---
# Anima3 提示词生成

Anima3 模型的提示词生成技能。将用户的中文场景描述转写为一条英文 prompt。

## Core Principles
- 严格按槽位顺序填充标签：[count/gender] → [character/series] → [appearance] → [clothing/state] → [pose/action/sex] → [expression/reaction] → [camera/shot] → [scene/environment] → [detail/mood] → [natural language补充]
- 仅输出1行全小写英文 prompt，标签间用 `, ` 分隔
- 禁止输出质量词(masterpiece/best quality/score_X)、画师名(@artist)、光线/光影/色调标签
- 禁止写权重语法，字段顺序即隐式权重
- 标签无法准确描述时末尾用英文自然语言短句补充
- 单人场景默认注入 `direct eye contact, facing viewer`

## Assembly Decision Tree

### 单人展示类（诱惑/暴露/自慰）
count/gender: `1girl, solo`
clothing: 选1-2件核心服装+1个状态
camera: full body; 诱惑 cowboy shot; 自慰 from above/close-up

### 双人前戏类（口交/足交/素股/手交/乳交）
count/gender: `1girl, 1boy, hetero`
clothing: 女方1件+1状态，男方 clothed male/faceless male
camera: 口交 pov; 足交 from side, feet focus; 乳交 close-up, breast focus

### 双人正戏类（传教士/后入/骑乘等）
count/gender: `1girl, 1boy, hetero`
clothing: 女方半脱/掀起/全裸; 男方 faceless male
camera: 传教士 from above; 后入 from behind; 骑乘 from below

### 特殊体位类
睡奸: sleeping, closed eyes, zzz; under covers/dark room
催眠: @_@, empty eyes, expressionless; fake screenshot
攻守反转: pegging/sitting on face/trampling/dominatrix
过激: Lv3-Lv4表情+身体反应; choke hold/rough sex

## 标签库速查

### 头发: long/short/straight/wavy/ponytail/twin tails/side ponytail/braid/hair bun/bangs/ahoge
### 颜色: black/white/blonde/brown/red/pink/blue/purple/green/grey/multicolored
### 眼睛: blue/red/green/golden/amber/purple/pink slit pupils/glowing/blank/heart-shaped/rolling eyes
### 体型: slim/petite/curvy/voluptuous/loli/shota/large breasts/small breasts/thick thighs
### 肤色: pale/fair/dark/tan/grey; 非人: cat ears/tail/vampire/fangs/demon horns/angel wings
### 服装: bra/panties/lingerie/bikini/school uniform/maid outfit/latex/kimono/hanfu/pantyhose/thighhighs
### 材质: silk/lace/see-through/sheer/latex/leather/transparent
### 状态: off shoulder/shirt lift/skirt lift/partially undressed/completely nude/torn clothes/wet clothes
### 单人姿势: standing/sitting/lying/kneeling/bending over/legs apart/spread legs/presenting
### 前戏: fellatio/blowjob/footjob/paizuri/handjob/fingering/sumata/teasing
### 正戏: missionary/doggystyle/cowgirl/reverse cowgirl/seated/standing/spooning/creampie
### 表情Lv1: blush/shy/looking away/flustered/small smile
### 表情Lv2: aroused/flushed/teary eyes/biting lip/parted lips
### 表情Lv3: ahegao/tears of pleasure/drooling/eyes rolled back/tongue out
### 表情Lv4: mind break/severe drool/huge tears
### 液体: drool/pussy juice/squirt/cum/creampie/cum dripping/sweating/tears
### 身体反应: trembling/legs shaking/arching back/hips bucking/sweating
### 镜头: full body/cowboy shot/close-up/from front/behind/above/below/side/pov/peeping
### 场景: bedroom/bathroom/classroom/office/dungeon/castle/throne room/park/beach/forest
### 氛围: motion lines/blur/depth of field/dark atmosphere/dreamy/magical

## Special Theme
NTR: split screen/from outside/talking on phone
BDSM: bondage/rope/shackle/collar/ball gag/blindfold
RBQ: used/gangbang/bukakke/layered cum/body writing
调教: collar/leash/crawling/on all fours/presenting/obedient
胁迫: blackmail/compromising photo/resisting→forced pleasure
偷窥: peeping/hidden camera/exhibitionism
事后: after sex/post-coital/nude/cuddling/satisfied
隐奸: head out of frame/under covers/dark room/implied sex

## Self-Check
1. 人数一致 2. 无互斥冲突 3. 无重复 4. 场景合理 5. 无灯光标签 6. 标签数单人16-30/双人22-38/复杂30-48

## Conflict Table
from front↔from behind; looking at viewer↔facing away; solo↔hetero
completely nude↔具体服装; pantyhose↔barefoot(除非torn)
missionary↔doggystyle; spread toes↔toe scrunch; rolling eyes↔looking at viewer
