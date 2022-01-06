<template>
    <div>
        <div class="container-company">
            <div class="wrap-btn">
                <ButtonCancel @click="onCancel"
                              class="btn-cancel"
                              title="キャンセル"/>
                <ButtonSave @click="save"
                            class="btn-add"
                            title="登録"/>
            </div>


            <div class="content">
                <el-form :model="company"
                         :rules="rules"
                         @submit.native.prevent="login"
                         class=""
                         ref="form">

                    <el-form-item label="temp" prop="name">
                        <span slot="label">会社名
                            <span class="required">*</span>
                        </span>
                        <el-input placeholder=""
                                  type="text"
                                  v-model="company.name">
                        </el-input>
                    </el-form-item>

                    <el-form-item label="temp" prop="username">
                        <span slot="label">会社コード
                            <span class="required">*</span>
                        </span>
                        <el-input placeholder=""
                                  type="text"
                                  v-model="company.companyId">
                        </el-input>
                    </el-form-item>

                </el-form>

            </div>

            <div class="wrap-btn">
                <ButtonCancel @click="onCancel()"
                              class="btn-cancel"
                              title="キャンセル"/>
                <ButtonSave @click="save"
                            class="btn-add"
                            title="登録"/>
            </div>


        </div>
    </div>
</template>

<script>
    import ButtonSave from "../../components/ButtonSave";
    import ButtonCancel from "../../components/ButtonCancel";
    import $Constant from "../../constant/Constant";
    import UserScreenConstant from "../../constant/UserScreenConstant";
    import CompanyAdminApi from "../../api/company_admin/CompanyAdminApi";
    import AlertService from "../../service/alert.service";

    export default {
        name: "CompanyForm",
        components: {ButtonSave, ButtonCancel},
        data() {
            return {
                company: {
                    id: null,
                    name: null,
                    companyId: null
                },
                isUpdate: false,
                rules: {
                    name: [
                        {validator: this.validateName, trigger: "blur"}
                    ]
                }
            }
        },

        methods: {
            onCancel() {
                this.resetForm();
                this.$emit('close')
            },
            getCompanyIdSelected() {
                let companyId = localStorage.getItem("companyIdSelected");
                return companyId;
            },
            async save() {
                this.loading = true;
                try {
                    let valid = await this.$refs.form.validate();
                    if (valid) {
                        this.loading = true;
                        let objectSubmit = this.$data.company;
                        objectSubmit.companyId = this.getCompanyIdSelected();
                        let response = await CompanyAdminApi.save(objectSubmit);
                        if (response.status.code === 1000) {
                            this.resetForm();
                            this.$emit('reload-table');
                            AlertService.success($Constant.MESSAGE_SAVE_SUCCESSFUL);
                            this.onCancel();
                        } else {
                            AlertService.error($Constant.MESSAGE_SAVE_FAILED);
                        }
                    }
                } catch (e) {
                } finally {
                    this.loading = false;
                }

            },

            resetForm() {
                let vueData = this.$data;
                this.loading = false;
                vueData.company = {
                    id: null,
                    name: null,
                    companyId: null
                };

                vueData.isUpdate = false;
                this.$refs.form.clearValidate();
            },

            validateName(rule, value, callback) {
                if (this.$utils.isEmpty(value)) {
                    callback(new Error($Constant.MESSAGE_REQUIRE));
                    return;
                }
                if (value.trim().length > 100) {
                    callback(new Error(UserScreenConstant.MESSAGE_INVALID_MAX_LENGTH_100));
                    return;
                }
                callback();
            },
        }
    }
</script>

<style lang="scss" scoped>
    .content {
        margin-top: 50px;
        margin-bottom: 50px;
    }

    .container-company {
        background: white;
        padding: 50px;
    }

    .btn-generate-password {
        background-color: #1fa9d8;
        color: white;
    }

    .wrap-btn {
        float: right;
    }

    .required {
        color: red;
    }

    .input {
        margin-top: 15px;
    }

    ::v-deep .el-radio-button__orig-radio:checked + .el-radio-button__inner {
        background-color: #1fa9d8 !important;
        color: white;
    }

    /deep/ .el-form-item__label {
        text-align: left;
    }
</style>