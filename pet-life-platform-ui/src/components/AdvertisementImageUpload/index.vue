<template>
    <div>
        <div class="advertisement-image-preview" @click="editCropper()">
            <img v-if="options.img" :src="options.img" class="preview-img" />
            <div v-else class="empty-preview">
                <i class="el-icon-plus"></i>
                <div class="text">点击上传广告图片</div>
            </div>
        </div>
        <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body @opened="modalOpened"
            @close="closeDialog">
            <el-row>
                <el-col :xs="24" :md="14" :style="{ height: '400px' }">
                    <vue-cropper ref="cropper" :img="options.img" :info="true" :autoCrop="options.autoCrop"
                        :autoCropWidth="options.autoCropWidth" :autoCropHeight="options.autoCropHeight"
                        :fixedBox="options.fixedBox" :outputType="options.outputType" @realTime="realTime"
                        v-if="visible" />
                </el-col>
                <el-col :xs="24" :md="10" :style="{ height: '400px' }">
                    <div class="advertisement-upload-preview">
                        <div class="preview-title">预览效果</div>
                        <img :src="previews.url" :style="previews.img" />
                        <div class="preview-tips">
                            <p>支持JPG、PNG、JPEG格式</p>
                            <p>建议尺寸与广告位匹配</p>
                            <p>文件大小不超过5MB</p>
                        </div>
                    </div>
                </el-col>
            </el-row>
            <br />
            <el-row>
                <el-col :lg="2" :sm="3" :xs="3">
                    <el-upload action="#" :http-request="requestUpload" :show-file-list="false"
                        :before-upload="beforeUpload" name="avatarfile">
                        <el-button size="small">
                            选择图片
                            <i class="el-icon-upload el-icon--right"></i>
                        </el-button>
                    </el-upload>
                </el-col>
                <el-col :lg="{ span: 1, offset: 2 }" :sm="2" :xs="2">
                    <el-button icon="el-icon-plus" size="small" @click="changeScale(1)"></el-button>
                </el-col>
                <el-col :lg="{ span: 1, offset: 1 }" :sm="2" :xs="2">
                    <el-button icon="el-icon-minus" size="small" @click="changeScale(-1)"></el-button>
                </el-col>
                <el-col :lg="{ span: 1, offset: 1 }" :sm="2" :xs="2">
                    <el-button icon="el-icon-refresh-left" size="small" @click="rotateLeft()"></el-button>
                </el-col>
                <el-col :lg="{ span: 1, offset: 1 }" :sm="2" :xs="2">
                    <el-button icon="el-icon-refresh-right" size="small" @click="rotateRight()"></el-button>
                </el-col>
                <el-col :lg="{ span: 2, offset: 6 }" :sm="2" :xs="2">
                    <el-button type="primary" size="small" @click="uploadImg()">确认上传</el-button>
                </el-col>
            </el-row>
        </el-dialog>
    </div>
</template>

<script>
import { VueCropper } from "vue-cropper"
import request from '@/utils/request'
import { debounce } from '@/utils'

export default {
    name: "AdvertisementImageUpload",
    components: { VueCropper },
    props: {
        value: {
            type: String,
            default: ""
        },
        // 裁剪框宽度
        cropWidth: {
            type: Number,
            default: 300
        },
        // 裁剪框高度
        cropHeight: {
            type: Number,
            default: 200
        }
    },
    data() {
        return {
            // 是否显示弹出层
            open: false,
            // 是否显示cropper
            visible: false,
            // 弹出层标题
            title: "上传广告图片",
            options: {
                img: this.getImageUrl(this.value),  //裁剪图片的地址
                autoCrop: true,             // 是否默认生成截图框
                autoCropWidth: this.cropWidth,         // 默认生成截图框宽度
                autoCropHeight: this.cropHeight,        // 默认生成截图框高度
                fixedBox: false,             // 不固定截图框大小，允许调整
                outputType: "jpeg",           // 默认生成截图为JPEG格式
                filename: 'advertisement'          // 文件名称
            },
            previews: {},
            resizeHandler: null
        }
    },
    watch: {
        value(newVal) {
            this.options.img = this.getImageUrl(newVal)
        }
    },
    methods: {
        // 获取图片完整URL
        getImageUrl(imagePath) {
            if (!imagePath) return '';
            if (imagePath.startsWith('http')) {
                return imagePath;
            }
            // 拼接服务器地址
            return process.env.VUE_APP_BASE_API + imagePath;
        },
        // 编辑广告图片
        editCropper() {
            this.open = true
        },
        // 打开弹出层结束时的回调
        modalOpened() {
            this.visible = true
            if (!this.resizeHandler) {
                this.resizeHandler = debounce(() => {
                    this.refresh()
                }, 100)
            }
            window.addEventListener("resize", this.resizeHandler)
        },
        // 刷新组件
        refresh() {
            this.$refs.cropper.refresh()
        },
        // 覆盖默认的上传行为
        requestUpload() {
        },
        // 向左旋转
        rotateLeft() {
            this.$refs.cropper.rotateLeft()
        },
        // 向右旋转
        rotateRight() {
            this.$refs.cropper.rotateRight()
        },
        // 图片缩放
        changeScale(num) {
            num = num || 1
            this.$refs.cropper.changeScale(num)
        },
        // 上传预处理
        beforeUpload(file) {
            if (file.type.indexOf("image/") == -1) {
                this.$modal.msgError("文件格式错误，请上传图片类型,如：JPG，PNG，JPEG后缀的文件。")
            } else if (file.size / 1024 / 1024 > 5) {
                this.$modal.msgError("文件大小不能超过5MB。")
            } else {
                const reader = new FileReader()
                reader.readAsDataURL(file)
                reader.onload = () => {
                    this.options.img = reader.result
                    this.options.filename = file.name
                }
            }
        },
        // 上传图片
        uploadImg() {
            this.$refs.cropper.getCropBlob(data => {
                let formData = new FormData()
                formData.append("avatarfile", data, this.options.filename)

                // 使用广告图片上传接口
                request({
                    url: '/public/admin/upload/advertisement',
                    method: 'post',
                    data: formData,
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }).then(response => {
                    this.open = false
                    this.visible = false
                    // 更新图片URL
                    this.options.img = this.getImageUrl(response.imgUrl)
                    // 触发v-model更新
                    this.$emit('input', response.imgUrl)
                    this.$modal.msgSuccess("上传成功")
                }).catch(error => {
                    this.$modal.msgError("上传失败：" + (error.message || "未知错误"))
                })
            })
        },
        // 实时预览
        realTime(data) {
            this.previews = data
        },
        // 关闭窗口
        closeDialog() {
            this.options.img = this.getImageUrl(this.value)
            this.visible = false
            if (this.resizeHandler) {
                window.removeEventListener("resize", this.resizeHandler)
            }
        }
    }
}
</script>

<style scoped lang="scss">
.advertisement-image-preview {
    position: relative;
    display: inline-block;
    width: 300px;
    height: 180px;
    border: 2px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    transition: border-color 0.3s;
    overflow: hidden;

    &:hover {
        border-color: #409eff;
    }

    .preview-img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        border-radius: 4px;
    }

    .empty-preview {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        height: 100%;
        color: #8c939d;

        .el-icon-plus {
            font-size: 36px;
            margin-bottom: 12px;
        }

        .text {
            font-size: 16px;
        }
    }
}

.advertisement-image-preview:hover .empty-preview {
    color: #409eff;
}

.advertisement-image-preview:hover:after {
    content: '';
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.5);
}

.advertisement-image-preview:hover:before {
    content: '点击编辑';
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    color: #fff;
    font-size: 16px;
    z-index: 1;
    pointer-events: none;
}

.advertisement-upload-preview {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100%;
    padding: 20px;

    .preview-title {
        font-size: 16px;
        font-weight: 500;
        color: #303133;
        margin-bottom: 20px;
    }

    img {
        max-width: 100%;
        max-height: 200px;
        border-radius: 4px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        margin-bottom: 20px;
    }

    .preview-tips {
        text-align: center;
        color: #909399;
        font-size: 12px;
        line-height: 1.6;

        p {
            margin: 0;
            margin-bottom: 4px;
        }
    }
}
</style>
