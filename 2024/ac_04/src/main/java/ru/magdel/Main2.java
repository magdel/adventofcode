package ru.magdel;

import java.util.ArrayList;
import java.util.List;

public class Main2 {

    static String input =
            """
                    SMXMMAXXXXMMMMSMMASASMSXMMAMSSMXSMMXMASAMXXMAMXAXXAMXAXASMSMSMMMSXXSXSXXAAMXXSXMASMAASXMXMSSMXXXXMMSSSMXSXXMASXMMSMSXMXSSMXSMMMSXMSAMSASXSAM
                    MASMMSXMMMMAMSMAXSAMXAXAXXAXSASAMASMMASAMAXMAXMMSXASMMMMSAAXAAAAXSMAAAAMAMXSSMMXMASMSAMXSAAAMSMMMSMAAAMMMXMXAXASAASXASAXMASMAAXXAXMAMXXSSMAM
                    MAMAAMXMAXSASAMMMXAMMMMAMMSMSAMXSAMXMASAMMSXSASAMXSMAAAMMXMMSMMXMAMSSMMAAMAMAXAAXMAMXAMSAMMSMAXAAAMMSMMAAXSMMSXMSMMSAMXSXMXXMMSSMMMSMMXMASXM
                    MAMMMXAMXMXXSASXAXAMSXMXSAAXMMMXMXSXMXSMMXMAXAMXXXXXSSSSXAMXXAMMAMXAXAXSAMMSMMMSMMSMSAMAAXMAMXSMSXXAXMSSSSMAXSAMXMMMSXAXAXAMMXMASXAMAMSXAMMX
                    SSSXXSMMSMMXSAAMSSSMMXSAMMSMMMMMMASASAMASMMMMXSSSSMXMAMMMXSASAMXXXSXMMMMASXAXASAMMAAMXMSMMSMXMXXAMXXSMAMMXMAMMXAAXMAMMSSMMXXAASAMMXSAMAMSSMM
                    AAXMASMAAASAMXMAMMAAXXMASAXXMAAAXMSAMXSAMAMXSAAAAXAAMAMAAXAMMMMSMAXMXSASAMMMMMAASMMSMAMAXXXMASAMXSAAMXAXMASXMSASMSMMMMMAXAAXSXMASAMSAMMXMAMA
                    MXMMAMASMMMXSXSASXSMMMMXMMSASMSMSAMXMXMMSSMAXMMMMMSXSASMSSMXXAAAMAMXMAMMAMXXAXSXMASAXSSSSMMSASAMASMMMSMSXAXXAAXXAAMAAXSMMMSMMAMMXXAMAMXMSMMS
                    MMXMAXXMMMAXMXMASXAXAAMMMMSAMXAMXMMAMXMMAAMXSXMXAAAASASAAXXSSMSSSMSSMSMSXMXXSXMXAXSMMMMXMMAMXMAMAMXAMAAMMMMMSMMMSMXMSMMXMXMAMMMSMMSSXMAAAXAM
                    MAASMXSAXSSMSAXXMMSSSMMAAAMXMMXXAXSXSASMSSMAMASMMXSAMXMMMXSAAMMXMAMAAAAXAASMMASXSMXXAAXMMMSSMSXMSSSXSMSMASAAAXMAMSMAMAMMXASXMSAMAAAMASXSMMSS
                    ASAMMAMXMXAAXMSAAXAAAASMMXSAXAMXMXAAAASMXMMASAMAAAMASXXMXMASMMMAMAMMSMSSMMXASAMAMAMSSMSAXMASAAXMXAXASXMXAXASMSMASMMMSAMXSASAMMASMMSSMMAAXAAS
                    MMAMMMSMSSMMAAMASMMXMASAAASASMAASAMSMXMXAXSAMASMMXSAMAMXAMXXMAMXSAMAXMAMAASAMASAMAMXAASMMMAXXMXMASMXMASMXSAMAXXXXAAAMAMAMXMAMAAMXMAMXMXMMMMS
                    XMAMSXMAAAXSXSMAMMXXXXXMMXMMMMSAXAXAXMMMMXMASAMAAXMXSAAMSSMXSSXMMMSSSMSXXMMXSMMXSXSMMMMAAMMMMSAMAXXXXMMAAAXMXMMSXSMMXAMXSSMSMMXXXMAMAMXMAMSS
                    SMSMXAXMMXMSMAMASXSXSAMXMAMMSXMASMMMAAAAXAMAMXSMMSMXSXSAAAMXMMXSAAAXAAAXMMSASAMXXMXMXSXSMSXAASAMSSMSAXMMMSXXXASAAXAMSSMMMMAAXMASMMSMSMSXXSAX
                    MAAMXSMMSXAXSXMMSXAAXMMASMSAXAMAMAAXMXSSSSSMSMAMXMMMMMMMSXMXAAAMMMSSMMMSMAAAMMAMSMAMAXAXMMMMXMAMXAAMAMXMXMXXMSMMSMAMSAAAAMMSMMMXAAXAMASAMMMM
                    MSMSAAAAAMSMMMXXXMMMMMSXSAAMSSMASXMMSMAMAMXMAAAMXMAXAASXMASXMMXSAMXMAAAAMAMMMXMMAMAMSSMSAMMXMSMMSMMMSSXXAAXMXAMMXXXMSXMMXMXMAXSMMMMAMAMAMAAX
                    XAAMXSMMMSXMMASXSMAXAAAMMMXXAXMAXAAAXMAMAMASXSASAMSSSXSASXMASAMMASASXMSAMXSXXMASASAMXMASAMXAMAXAAAMAXAASMSXMSMSAMXMMMMSMMMSMMMXAASMSMSSSSSSS
                    SMSMMMMSMXMAMXSAASMSMSMXAXMMASMXSSMSSSMSASMSAMAMAAMAMXSAMASXMAMSAMASXMAXMAMMAMXXASASAXXMXMSMSMSSSSMMMMMXAAMXAXMXMSAMXAAAXASAAMSSMSAXAXAAMAMM
                    MXMAAMAMAASAMAMXMMXAMXXSMSMMAMMAMXAAAAXSASXSASXXMMMAMXMAMXMXSAMMMMXMAXXXMASMSMSSXSAMMMMSSMMXAAAAXAAXAXXMMMASMSSSXMSSXSXSMASMMMAMXMMMSMSMMAMM
                    XASMMSASMMSASXXAMXAMSSMXAAAAASMMSMMMSMMMAMMMAMXXMXSSMMSAMAXXMMMAAMXMASMMSMXMAAMSAXXXXMAAXAAXMMMSMSMSMSAXAMMXXAAMAMXSAAAXMXMMMXXMXMXAXXAXMXMM
                    SMXXXMXMAMXAMXXSSMMXSAMMSMSSXSAMXXAMAXAMAMXMMMSXXAAAAXMAMAMXASMSSMXAAXAAMAMSMSMSXMASMMMSSMMXSAAXXXAXAMMXMSXSMMSMMSAMXMMSXAAAMAXMASMMMMMMSMSM
                    SAMSMMSSSXSAMXXMAAXXMAMAXMAMASAMXSASAMMMMMMXMMXAMSSSXMSAMXXSAMMMMAMSAMMMMAMXAMAXMAMXXAMAXXAAXMSMMMMMSMSMXMAXXAAMAAAXAMXXXMSSSMAMAXASAAAAAMAX
                    MAMSAMAAXMMAMXMSAMXSSMMSSXMMMMXSASXMAMMSMSMAMSMSMAAAXASASAAMMSAAMSMAAXSASASMSMAMSSXMASMMASMSSXMAXXXAXAAMAMSMXSXSMSMMAASAXMAMAMMSMSASMSMSSSMX
                    SAMMAMMSMSMSMAXSASAMAMXXMAMXMASMMSMMSAAAAASASXAXMMMMXMXAMMMMAMXMSMXMSMSASAMAAMXMAMAAAXAMAMMXMASXMASMMMMSASAMXMMMXAXXXMSAMXASMMAAXXXXXXMAXMXX
                    MXMXMMMAAAXAMXMMXMMSAMXMSSMAXMXMAMXAMXSMSMSMSMMMMSMSSSSSMXXMAMSSMXMMXAMMMMXSXSMMMSXSSSXMAXMASAXMAMAAASXSXSASXXAXMASMAXXMXSXMAMMSSXMSMSMXMXMS
                    SMSAMASMSMSXXAMMAXXSXMAMXMASMXAMAXMASAXXXAMAXAXAXAASAAXMAXXMAMSAMXAAMSMXSMXMASAAMXAMAMXSAXSAXXXXMSSSMMXXASMMMSSMMAXXXMASMXASMMSAMAXMASAXSAMX
                    XASXSASAAXAMMSXMMSMMSXMSASXMXSMMMSMAAMMMMMMMMMMMSMSMMMMSAMXMXXSAMXSXXAXAAXSSMSSMXMSMMMMMMXMASMSMAAXMMSSMAMMAAMXAMASMMSSMASAMXMMAXXAMXMAMSAMS
                    MXMAMASXSMXSAXASAMAASXMSAXASAMMSAAMMSXAXASASAMAMAMAMASMSXMXMMMSAXAMMMSMMSXMAXMASMSMSXXXAXSMMXAAMMMSMAAXMAMMXSSSXMXSAAXXXXMXSXXSMMSMXAMMMSMMA
                    AAMAMAMAXXAMMSAMASMASAMMAMSMMAAMXMMSMXMSMSASXSAXMSASASAMXXAXAAMMMXSAAXAXMMSMMSAMXAMSXXMASXMMMSMXSAXMMSSSMSAMAAAASXMMSMMMXMAAMMSAMASMXSAAXAMX
                    SXSXSXMMMMSSXMASMMMXSAMMAMXAMMSSMMSSMSMAAMAMASASASASXMAMXSMSSSMMSASMSSSMAAXSXMXMSXSMAMSMMAXMAAXMASMXAXXMASMMSSXMXAXSMXAAAMAMSASAMAMMMSMSMMMA
                    MAMAXAMSSXXAXMAMXMSMSXMXMXSXMAMAMMASAAXAMAAMMMAXAMAMXMAMXSAAAAAAMXSAAAAMMMSMXSSMAMAMAMAASMMMSXXAXXXMMSMMMMXAAAMXXMMMASXSXSSXMMSXMAMAASXXAMXM
                    MSSMMXXAXMSMXSAMXMAASASASXSSSXSAMXMMSMMMXSSSSMSMXMSMMSSSMMMMMMMMSMMMMMMMAXAAXSASASAMXSMMAASAAAXSAMXXAAAMMXMMSXSAAMAMMMXXMAMAMXMMSSXMMSASMMAM
                    AAAMMXXSXMXXASMSAXMSMAXASAMASAMXMSXAMXASMMMAMAXAXXXAAAAXAXXXXMXMAXXMASXSSSMSMSAMXSASXXMASMMMXSXMASMMSMSMMXSXAMMMMMASMSAMXASAMASXAAXSAMXMXSAM
                    MSSMMMMMXSMMMSASXSSMMMMMMXMAMSMAMXMMSSMMAAMAMMMMSSSMMMSMSXMMSMASAXMMMXMAXAAAAMAMXSAMXXSAXMASAMMSAMMAMSMASMMMMXSAMMXMASASXMSXSSSMMMMMASMMXXMX
                    MXAAAAAAXMAMSMMMMXXAAXSXMXMASMSMSXXAAAMSSMXMSMXAAAAAMAXXMASAMSMMMXSAAAMMMMMSSMMMXMAXSAMXSSXSAMAMSAMXMASAMAAAXXMASAMXMSASMASAXAMXSAAMAXAMXMSA
                    ASMMSSXMMMAMMAMXAMSMMSMAMAMXMXAMXMXMMSMAMXAXAMMMSSSMMASAMAMAMSXXAAMMXXMXMMAXAXXSXSMXMAMAMAMSXMXSMMSMSMMMSSSMSXSAMXMXAMAMMAMAMAMAMMMMMSMSAAMX
                    MSAMXXXASMSSSSMSSMXAMAXSSMSAXSXMAMMSAMMASMMXAMMMAMAAMMMMMASMMMMMMMSSMSMAMSMSMMMMASMXMXMXSAAMASXMMMAAAXAMAAAAXMXMMAMXMMXMMSMMAMMXMAXXXAAMMSMM
                    XSAMAMSAMAAAAXAMMXMASAMXAAMXMAMMASAMMXSASAXXSMAXASXMMMSMXASXASXMSAAAAAMAMAAAXMXMAMMXMAXASXMSAMAMXSMSMMMMMXMAMXAASASMXMXSAXAMXMAMSMSSMMSMAAXX
                    XXXMAXSXMMMMMMSMMAAMMAMSMMMMAMASMMMSMAMSMXMAXSXSAMXAAMAMAMXMXMAAXMSXSXSASMSMSSXMASXASMSMXMXMAMXMASAMASXMSMXXAMMMSASAAXMMASMMAMAMAMXAAXMASMMM
                    XMSSSXSAXMAMXMXASXSXSAMMAMMAMXXAXAMSMAXAMAMMMAMMMMMSXSASMMSXSMMMAMAAXMSAMXAMXXAMASMMAAXMASXMAASMMSASAMSAAXMSSSMAMMMXXSAMXMASMSSSSMSSMMXAASAM
                    AXMAMASAMMASAMSMMSXMSAMXAMXSXSMXMMSSSMSMXAXAMXMMASXAAMASMAAASXMAAMMMSAMAMSMSAMXMASASMXMSAMAAAXXAXSXMASXSMSAAAAAXXAXXMAMSASXSMAMAMXMAMXMMSMSX
                    MSMMMMMMMSASXXAASAMAXAASMMMXAAMASMAXXXAXSSSMSASAAXMMSMXMMMMXMASXXAXAMAMXMAAMAAXMXSAMXMXMASMXSSSMMXXXMMMMMSMMSMMSMSMSAMASASXXMAMAMMMAMSXXMAMX
                    AAMXAXAXMAMMXSSMMASMMSMAMAMMAMMAMMSMSAMXMMAASASMAMMAXMASAMXXSXMMMMMMMASMMMSMXSMSMMMMXMAMMMXAXMASASMMSAAMASAXXAAXAAASMSAMXMAMSMSASXXAMXMMMAMM
                    SMXSASMSMSAMXAMXMAMXAMXMMAXAMXMSSMAAXMXMSMAMMXMXXAMXMXMAMAMXMAXSAXMAXXSAAAMAMAAAMAMMMMMSMAMMMSXMXXAASASMASXMXMMMSMXMXMASMXSAAXAAAMSMMAMXMAMA
                    MXMAMMMAAXASXSSXSAMMSMAXSSSSSSXAAMMMMMAMAXAXSMSASXMSXMMMASXMSAASXMMSSXMXMXXAMMSMSASMSAAXMAMXAMASMSMMMAAMMMASXXXAXXXMSMXMXAMMMMMMMAAXXASMSSSS
                    MASXSAMXSSXMMXAXXAXAASXXMMAAAMMSMMXAASASMSMSMAMASAASAMAMAXAMXMMMXSAAXAXSMSSXMXMXSASASMMMSSSXMSAMXAAMMXMSXSAMXMMMMMXMAXMXMXSAASXSMSMSSMSAXMAX
                    SAMAMXSAXMASXMSMSSMXMAMXSMMMMMAAASMSXSXXXAXAMXMXSXMMAMXMMSMMXXXAAMMMSSMSAXAAXXMAMAMMMMSMAXAMMMASXSMXMSMAAMMMAXAAAAMXMSAMXSSXXXAXAAXXAAXXXMMM
                    MASMMMMXSMMSXSAAXAAMXSSXMASXSXMMSMAAXMASMMSXSXSXSXMMMMAAMAXXXAMMMMMXAMAMMMSMMAMSXSAXXXAASXMMSSXMAXXMXSMMSMASASMSMXSAAAXASMMXMMSMSMSSMMMMMSXA
                    XMAMXXSMXAAMMSXSMMMMMMAXXAMAXAMSAMXMSMAMAAAASAMMSXSAMSSMSASMMSAAMMSMMMAMXAAXAMXXAMXMASMSMAAAXXMMMSMMAXMAAXXMAMAAAASMMSMSMSAASMXAMXMMMSAAAMXS
                    XMASMMXAMMMSASAXXXMSAMXMASMMSSMAMXAXXXXXMMMMMAMASASAMAAXMAAAXASXSAMASXSSMSSSXMAMXMAXXAXAXMMMSXSAAAAMSSSSSMSMMMSMMMXMAXXAXMSMSAMMMAMAAXXXMMMM
                    XMAMASMSXMAMMMAMXMASMSXSXXAXAXXXMXMSSMMMXAASXSMMSASXMMSSMSMSMMMMMASMSAXXAAXMMMASAAASXSSSSSSXSASASMSMAXMAXAMXMAXAXSAMXSAMXMAMMMMASASMXXSSXSAS
                    MMASXMAXAMMSAMAMASXMASAXAMSMSSSSSSMAXAAASMMSAMXXMAMAXSAXAXXXAAAXSXMAMAMSASASXSXSAMXSAAAXAAMAMMMAXAAMXSMAMXMAMXMAMXASAAAXMXASXXSMSASMSAAMMMAS
                    XSAXAMMSXMXMASASASAXSMMMMXAMXAAMAAMASMMMXAAMAMXMMXMXSMMSAMASXSSXMAMXSAMXXMASASXMAMMMMMMMMMMAMAMXMMMSAXMASMSMSSSMMSSMMMMMMMASAMXXMASAAMXSXSAS
                    MMXXAMXSAMMSAMXMASMMMAASMMSSMMMMSMMASASMSMMMSMSMSSSXSAAMASXXMXXASMMAXXSXXMSMMMASMMMAAAXAMSXMXSSMMSAMMSMXXAAAXAAAMXMASXMASMSMMAAXMSMMMXXAXMAM
                    AAMSMMAXAXAAMMMMMMMMSSMSAAMXMMMXAMMXSAMAAXXXAAXAAAXASMMSAMXXSASMMMMMSMMMSSXASMAMAASXSSMMMASMAXAAAMASASXSMSMSMSSMMAMAMXMAMXXAMSMMMMAASMAMXMAM
                    MXMAXMXSMMSXMAAMAAXXMASMMSMAAASMMSMAMAMSMSSSMMMMMSMMMSMMAXSAMASAAXAAAAAAXXSMMMASMMMMMMAAAAAMMSSMMSAMAMAAAXAAAXAAMAMAMXMMXSSMMMMAASMMSXAXASAS
                    AMSSSMASAAAASXMMMSMSXMASAAXMMXMAAAMASAMXXAMASXSAAXXSASMMAMXMMAMMMMMSSSMSSMMMAMXMASXXAMXMMXSMXAAXAMAMAMMMXMSMSSMMSSSSSMSAXXAXMASXMMAMMMXMXSAS
                    XAXAXMASMMSXMAMSXXASAMXMSSXSSMMMMMSASMSMSMSAMXXMXXAMAMMSAMXSMMSSMXMAAXAMAAAXXXSXAXXMSSSMSMXXMMSMSMSSSSSSXAXAAAAMXAAMSAMXXSAMXAXMAMSASXSXMMMM
                    ASMSMMASXMMXMAAAAMMMXMMMMMMMMAASXMMAXAAAMXMAMSSMMSMMSMMASXAXXAAMAAMMSMSMSSMSSXSMSMXXAAAAXXSMSMMAMSAAAAAXMASMSSMMMMSMMMMXMAMAMMMMMXMAAAXMASAS
                    MAAXXXAXAXSXSMSMMMSMASAAAAAMMSMMAXMSSSMSMSSSXAAMXAAAXMSAMMSSMMSSSXSAMXXAMXAXMAMAMMMMMSMMMMAAAAMAMMMMMMMMMMAMAAAXXAXAMXMMMAMSMXASXSMMMXMASAMS
                    XMSMSMMXSMSAXAMXAAASAXXSSSXSAMASMMXAXAAXMAAMMSMMXSMMSAMASAAAXAXAAXMASMSMSMSAMXMAMMAAAAMAAAMSMSMSMMAXAAXMXSMXSSMMMXMXMSAASASXMSASAAXAXSAMAMXX
                    MXXASASAMXMAMSMSMSMMSSMMXMAMMMXMXMMMSMMMMMSMAMXMAMAMMXSSMMSSMMMMMXSXMASAXXMAMXMASXSMSASMSSMAMMAXAXMXSSSMAMXAXAMMSMSMMXMMMMMAXMMMMMMSXMASMXMA
                    MXXMMAMXXMMSXAAXAXXAAMXSAMXSSSXMXSAMAXAAXXXMMSAMASXXXXSXAAAAAXSXXXMAAMMAMXXMMSSMSMXXMXSMAXXAMSASMMSMMAMMAMMMMXMASAAASMMXAAMXMAXSAMAXXMAMXAAA
                    MSMSMSXMASMMSMSMMMMMMSASASAAMXASASASASMSSMMMASAMMMMMMMMSMMSSSMMXASMAMSSMSSMAMAAXSASASASMAMXMMSXXXAAAMSMSASXSASMAMXXMMAMMSMSMSSMMAMSSSMXXMMMM
                    XAAAAXAXAAXAMXAAXXXAAMASXMMSMSAMASAMMMXXMAAMASAMSSMAAAXAMAAXMAXXMASAXXAAAMSAMMSMMXMAMXSMASXSASAMMSMMXAAMASAXASMASXSMSSMAXASAAXXSAMXAMAASXMSS
                    XMMMXXXMSSMMSSSSMMMXXMAMAXXAMXAXAMMMMSMSSSMMXXAXAAXXMSMMMMASMXMSXMSMXSMMMXSMSXMXMAMAMXMMAAAMASMAAAMASMSMAMMMMMMXSAAAAXMXSAMMMMMMSXXMXMASAAAM
                    XSSSMSAAAMXAAMAXAXASMMSSMSSSMSSMXXXAXAAMAAASMSSMSSMMXXAMAXAMXXSXAMXXAMXSXXMASMSMSXXAXXSAMMMMXMXMAXXXMAXAMXSAAASAMMMMMMMXMXMAMXMAXMMXAXASMMMS
                    AXAAASMMMSXSMSAMXMMSAAXAXAAAMAXXXSSMSMSMMMMXAAXAMXXSASAMSMSXSXMXAMAMXSXXMMMXMAAXMAMSXMMXSMMXXXMSMSXMXSXMXAMXXMMASMXXSXSASASASMMAMAMSMSASXSSX
                    XMMMMMXXXAAXXMASXSXMXMMSMMSMMMMAMASAAMAAXAXMMMMMMAMMAMMMMAMXSASXSMXMAXMAXAXXMSMMMSMXASMSAMXAMAMAAAXMMXAMMMSMSSSMMMSMMASASASAMAMXMAMXAAAMAXXS
                    SAAAXAAMMMMMMSAMAMMSSMMXAXXMASASMAMXMSSSMSXAXAXAMAXMAMMAMAMAMAMAMASXMSSMMSMMMAMXMMAMAMMXXXAAAAXMSMMMAMAMAMAMXAAXXMAMMAMMMAMASXMSSSSMMMXMXMXM
                    ASXSSMMMAAAAAMMMSMSAXAASXMXMASAXMSSMXXMAAAASXMXSSSSSSXMASAMAMAMXMAMAXAAAXMAMSMSAAMAMSMMSMMSMSMSMAXAMSSSMXSASMMMMMSSMMASAMXSAMAAXAAXMXMMXMASX
                    MMXMAAASMSSMSXXAAXMASMMSXXXXXMMMMAAAXSXXMMAMAAAXMMAAMMSASXSSSMMXMASXMXMMMMSMMASXSMMSAAMAAAAAAAAMMMSMAAAXAXASMXAXXAAXSASXSASXSMMMMMMXAXMAXXXM
                    XMASMMMSAAAMXMMMXMXMXAAMMSSMMSMMMSSMMSXMXXXSMMMSAMMMMAMAMAMAMASMSASMMMASXAAAMAMSMAMAXXMMMXSSMSMAXAAMMMMMMSMXMSXSMMSMMASAMAXAXXAAMAMSMSSSXMAA
                    XXAMAAAXMXMMXAAAAMASMXXSAAAAAMAMAXAXAXAMMSMSXSXSAMAAMXSXSASMSAMAMASAASMXMXMXMMMXXAMMMMMXSAMAXXMMMSXMAAMAAAMAXSXMAAAAMXMAMMMSMSSSMAMAMXAAASMS
                    SMSSSMMSSSXMSSMSMSASAAMMMSSMMMAMMXAMSSXMAAMSAMXXXMSXXMAASASXMXSSMMMMMAMAMASMSMSSSMSXSAMAMAXSAMXXXAAXSSSSSSSMMSXXXMSXMXMASXAMAXMAXXXAMXMSMMAM
                    MXMAAASAXMAMAAMAAMAMMAXAMXAAASXSSMMMMXAMSMSMMMSMXMXSMAMMMMMMMMMAMSAXXMMAMMAAAAAAAAAAASMSSSMMAMSMMMSMAAXMAMAMAMMMMXXXSSMSSMXSSSSSMMSSSXMAMMMM
                    MSMSMMMMSSSMSSMMSMXMAMMASXXMXSMAXAAASXMMXXXXMAMSXSAMSXMAMXAASXSSMMXSMXSSSSMSMMMSMMMSMAMAAXAAAXAAXAAMMMMMSSSMMXAXAAMXMAMAMMASAMAMAXAXMASASAMX
                    XAAAXXAXMXAMXAMAAXXMAMSMMMASASMSMSSMSAMAXMXMASXAMMAMAMMSASMXSAMASMAXAMXAAAXXMXXAMAAMXAMMSMXXMSXXMMSMMASXAAXAXSSMSASXSAMAAAMMMMASAMXMXMXMMMMM
                    SMSMSSSSMXMSSXMSMSSMAXAAASMMAMAAMXMXXAMSSSXXAXMMMSMMXSAMXAXAMMMAAMAXMAMMMMSMSMSMSMSSSSSXXMAAXMMSSMXAMASMSMMXMXXAMAMMMXXSSXMAMMMXMAMSXSAMSMSM
                    AMAAMAAXASXAAAXAAMMSMSSXMXAMXMSMXAXXSXMXAMMMXXAXAXAAXMASMSMMSXMSSMMMSAMXAAAAAXSMMMAAAAAXAMXSXAAAAASAMAMAAXMAXAMMMAASXAMMAMSASAAMASMAAMMMAAAS
                    MSMSMMMMMMMMSSMXSMASXAXSSMMSAAMASXSMASAMAMMAMMSMSSSMXSAMAXAXAAMAMAAAMAMSMSSXSAMXSAMMMMMSASAAMMMSMMMMMMSSMMXAMMSSMSXMMXSMAMSASMSMMMMMSMSSMSMS
                    XAMXMSXMXAXXAAMAMMAMMXSAXAAXMMMAXAAAAMXSAMMMSAXAAAAMMMASMSXMSMMASMMMSMMXAXXAXASASAXMXMXMAMMXAXAAMAAAXAAAXSMMSAAMMXAXSAAMMMMMMMXXAXXAAAXMAAAX
                    AMXXXAASXMSSMMMXSMMSAMXMSMMSSXMXMSMMXSXSASAAMMMMMSMMSXAXAMXMAXSAMXXXAAXMSMMSMMMXSAMXASAMXXAASMSSSSSSMMSMMASAMXMSASMMMXSXSMSASMMSSMASMMMMAMSM
                    MSMSMSMMSAAXXXMMSAMXMAAXSXAAXXSSMAMSMSXSAMXSMXMAMAAAMMMSMXMMAXMASMXSSSMAMMAAAXAXMMMSMSMSXMAXXAMAAMAMAMMMSAMMXMXMAXSAMXXAMXSASMAAAXAMXSAMXMXM
                    XXAAAAXAMMSSMASAMAMMSMSXSMMSMMAXXASAAMAMXMSAMAMAMSXMSAMAXSMMSSSMAMXAAXMXMMMSSMSSMAXXMSXMAASAMXMXXMAMXSSXMAXXAMAMMMSXSAMAMXMXMMXSMMSAMXSSXSAS
                    MSSMSMMMXMXAMMMSMXMXSAMASXMAMMASMMXMXMAMAXAMSAXAXXAMSASXMXAAAXXMAMMXMMXSAMAMXAAAXMSAXMAXMAXAMMMASXXSAXMAXXMXASASAAXXMXSAMMMAMAXXXXMAXSAMASAS
                    XAAXMXMAAXSSMMAMXAMAMAMAMXXAXMAMAASXSSSSSSMMSMSMASAMSAMXMMMMSMSMSSMAXAMSAMSXMXSAMXMMMSMMMSXSMAMAMSAMAMSSMMSXASXXMXSMAMXAMAXASMMSMXSSMMAMAMXM
                    MSXMXAMSMXAMXMASXXMAXAMSSSMSAMXSMMMAAXAAXAAAXMAXMMMMMAMXMASAMAASMAXAMXMMXMXAMMMXMASMMMAXXXAXSMMMSMXMXMAMAAXMXMMXSSMAASXSSMSAMMAAMAMXXSXMSSSS
                    MMXMSXMAXMAMXMASMMMMSASAAXAXMAMMXAMMMMSMSSMMSSMSSXSASAMXXAMASXMMSAMXAAAXMASXMXMSXAAAAMAMMMMMAMSMAMXXXMXSMMMXMAXAMAXXASAXAAMXMAXAMXMMMSAMMAMX
                    XAAXXMASXSMMXMASAAAAMMMMSMMMMSAASXSASAAXAAMXXAXAAXSXSASXMAXMMAXXMSSMSXSASASAXMASMSXSMMAMMAMSAMXSASMMXSXSAAASMMMXSAMMMMMMSMXXMMSXMSAMASAMMAMA
                    MSMSASAMXMAMXMASMMSMSXMXXAXSAXMXMASAMMXSSXMASXMMSMMMXMMXAMXMSAMXAAXMMXMXMAXAMMMMAMMMMSMSXXMSXMXSXSAXMXASMMXMAMAXMMMSMAXAXXMXXXSAAAMMASXMSASX
                    XMXSXMXSAXAMXMASAMAMMASASMAMAXAAMXMAMMMAMAXMMMMXXXSAMSSSSSXMAMXMMMSMMASAMXMSXSAMMMAMXAXMASXMSAXXAMMSAMXMSMASMMSSMSAMSMMASMMMSASMMMXMAXMXSAMX
                    XMAMXMASMSXSAMXXMMXMMAMASMSMSMSASXSAMXAAXXMMAAMMMXMXSAAAXXXAXXAAAAAAXAMXMXXAASXMXMMSSMMMASAAAMMMXMXSMSXMASMXXAXSAMXXAXSXXAAMMMMAMMXSXXSAMAMM
                    SMSMXMAXXAAMASASASAMMSMMMMAMAAMASAMXMXMXMMMMAMXAAAAXMMSMSMMMSSSSXSSSMASXMSMMXMAMSSXAMSMMAMMMMXXAMSMSXSMSMSXXMSMMMMMSSMMMSMMSAAMSMSASXMMASXMA
                    SAAAAXXSMXSAAMAAAMMSAMAAAMAXMXMXMASASAXAMXAMSSSSSMSASAAAAAASMAMMMMAXXAMXMAAXXMASAAXMAAXMSMMASAMXSAASASAAXMMXSAAAMAAMXAMAXMASXSMXAMASXXXAMXXS
                    MXMXSXMMMMMMXMXMSMXMASXMSSMMMAXXSAMASXSAMSAMXAXMXAXMSSMMSMMSMMMMAMSMMMSSSSSXXXSXMMXXMMXMXASXSAMXSMSMAMMMMAMXSMSMSMSXSAMSSMASMMXMAMAMAAXSXMXM
                    SSMAXMMAAAXXAMMXAMAXAMAXAAXAASMMMASAMXMAXXAMMMMXMXMXMAMMMMMXAXAXXXAAXASAMXMMSMAMXXXSSXSASAMXSAMXXXAMAMSSSMMMMAXMXAXMMSXXAMXSXAASAMAMMMAMAMSX
                    SAMASASXSSSMXMASMSXSAMXMASMMMMAXSXMASXMAMSMMAAXSMMSSSMMASXMSSSMMSXMMMMXAAASAXXMAXMXMAAMMMAMAXAMAMSMXXSAXMAAAMSMSMMMXAMXXAXAMXSXSASXSMXMXAMAM
                    SAMASMMMXMAAAMMSAAMMAMXXXXAXASXMAMSAMMSMAAXSXSAAXASASXMMSAAAAXMASAMXAXXSMMMSSMXSXSAMMMMASMMXSSMSMAMSMMMSSSMSSXAXAMAMAMSSSMASMMAMMXMXMXMSMMAM
                    MMMMMMAXAMXSSSMMMMXSAMMSMXMSASAMXAMASAAMSMXXAMXMMXSAXMMXSMMMSMMMSAMASMMXAXAMXAXMASMSSXMMMAXXAXAXSMMAAAXAMAAMXMSMXXXSAMXAXSXMAMAMXAMSAMMAMMSS
                    XSASMSSSMSAXAAXMAAMSAAAAASXSXXMASASMMXXXAXSMXMAXXXMMMXMAMXXXXAXXMAMXAAASXMMSSSMMAMMMAASXSMMSMMMMXMSSXMMSAMXMAMAAMSMMMSMAMXMSMSMMSASMAMMASAAX
                    ASASAAXAXMMSSMMSMSMSSMMSSXMMASMXSASMAXXSASXMASASMMXSAMXSMMMMMMXSMMSMMMXMAAAAMASMAMAXSMMXAAAMXSAAAMAMMAAMMXMAMSMSMAAAXAMXMAAMXMXAMMXXMMSAMMSM
                    AMAMMMSMMXAAAAXXAMXMASMXMMAMAMAAMAMMXMXMXMXSAMSMXAMSAMAAXAAMASASAMXAXXMASMSXSAMSMMMXAXXXMMMMASXSXSASMMMSSSSMXSAMMSSMMXMASAMSAMMXSMMMMMMMSMMA
                    MMMMMAMXAMMSSMMSMSASAMXMASMMSSMSMXMAMSAMXMAMXXAMXXASMMSMSXMMAXASMMSAMMMAAXXAMAMXXASMSMSMMSAMXMAMXMAMXAAMMAAXAMAMXMXMAASMXMAMAXMXAMAAXAAASMMS
                    XASASXSXSSXAMMMSASAMAMAMAMXMXMXXMAMXXSXXXMASMSASMMMXMAMMXAMMSSMMXAXAMAMXSAMXMSAMMXSAMAAAASASMSXSAMAMMXMXMSMMXMAMXMAMSXMASMMSSMMSASMMSMMMSAAX
                    SXSASASMAMMMMSAMXMMSSMAMMSAMAMXMSMSMMMSMXMASAAAAXXASMXSASAXAMAASMXXMSXSXXMAMXMAXSAMXMXSMMSXMAAMMASXSXMMMXAMAMSMSAMSMMAMAAAXAMAXSAMXXMXSASMMS
                    SAMAMAMAAXXSAMXSAMXAAXXMAXASMMAXXMAXAAMMAMSSXMMMSAASAASAMAMSSSMMMMSMAAMMSSMASMXMMASXMXMAMSMMMMSSMMAAXMAAXMMAXAASMXMASXMSSMMSMSMMMMSAMAMXMAAS
                    MAMAMMMSMSXMASAXASMMSSSMXMAMXSMXSMAXXXAMXSAMMXSASMAMMMSAMXMAAAMAAAASMSMAXAMMXAMXMXMASMMAMXAMAAXXAMAMMSASXXSSSMXMMSMMMAMXAXAAXMAXSAXMMSSSXMXM
                    SXMASAAAMXASMMMMMMMXAXMAXMXMMAMASMMMSAMXMMXMMMMASMSXXASMMSAMXMMXMSMXSAMSSMMXXMSSXMMAXASXMSSSMSSXMMSXAMAXXAXAXXMMAAAASAMSXMSMMSAMMSSXMAAMMSSM
                    MAMAMMXMASXMAAAASASMSSMSMSAAAMMAMAAASMSXXSMMSAMXMAXMMXSMASXXMXSSMXAMMMXAMXAAASAMASMSMMMMAAAAAXAASAMXMXSMMXMXMMXMSSSMSAMXAXXXMXXAAMMAMMMMXAAM
                    SAMXXSMMAMASMMSMSASXMAAXASXMXXMSSSMMMXMXMXAMXAXSMMASMMXMASMMSAMXAMSSXMASXMMSSMASAMXXAXAMSMXMMMAXMASAMAMAXXMXXMAMAMXMXMMSMMMMMMMMMSSXMASXMSSM
                    MMXSAMAMASMAMXMAMAMASMMMASMXMAXAAXAXMAXASXSMSMMXXSAMAXXMAXAXMAMMXMAAMSAMAMXXAMMMMSSSMMXXAAASXSXSXXSAMASAMAXSSMXMMSAMXSAMAAXAAAAAMXMMSAMXXAAA
                    MAAMAMMSASXXMAXASASMMXXMXMAASAMMSMMASMSMXAMMSXAXMMMSMMSMMSSMXSSMMMXSMMASASMSMMXXXAXAAASXMSMSASASMMSASAMMSMAMAMAXXSASAMASXMXSSSSMSAMMMASMSSXM
                    MMSSMMASASMMMXSAMAXXSXMASMSMSAMAMASAAMAXMXMASMXMXSXAXAAAASAMXAAMXSAMAXXXAMAAMSMMMMSXMMMAXMAMAMAMMAMMMMXMAXXSSMSSXSAMXSMSASAMMAAMSASASAMXXAMA
                    MAAAXSXMASAXAAMXMAMMMXSASAMAMAMASMSMMSAMXSMMMMXSXMXSSSMMMSASMXXMAMASMMSMSMSMXAAAXXAXSMSMMMAMXMAMMSSSMMASMSMAMAXMAMXMXMAMAMSMMSMMSXMASASAMXAM
                    MMSSMSAXAMXMMSSXMASMAAXXSAMSMASXSAXMXMAMAXMMSSMMAMSXAAXAAMXXXSSMMSAMASAAAAAMXSMMSSSXSAAMMMXAXSMMMMXAASASAXMAMSMMAMASAMXMAMAXXAMXMSMXMMXXAAXS
                    MAMMAMXMMSXSAMXMSASMMMSMMAMXSXSXMMMSASXMMMSAAAASAMSMSMSMSMMMMAAAAMASMSMSMSMSAXAAAAMAMSAXAAMMMASXSSSSMMAMMMSASAAXSSMSXSXSXSMXXAXAXASAAMMMSSSM
                    MAXMSMXMAXXMMMSAMXXXXAXAXAAXAMXMXAAXMSMMSAMMSXMAMMXAAXAAAXAAXMSMMSAMXSXXAMAMASMMMMMAMAMSXSAMMAAXAAAMAMXMMXSMSXMMAAASAMAAXAXXXMMAXMMSMMAMAMAM
                    SMSAAAXASXXMMMAMMSSSMXSMMSMMXSAMXMXSASAAMAMAXXMASXMSMSMSMSSSSXXAMXMMMSAMSMSMMMXMAMMAMAXMAMMXMMMMMMMSSMMASASXMASMSMMMAMAMMMMSMSASMSMXXSXMXSAM
                    AAMXMMSAXASASMSXAAMAXXAAAXASMMAMSAMMXMMMSAMXMASASAAXAXXXAMXAMMMAXMASAMMXMAAMAMXXAASXSSSMMMSSMMXMAMXXAASAMXSXMAAMASXXAMASAAXAAAAXAAASMSASMSMS
                    MSMSMAMMAMMASAAMMXSAMXSMMSXMASMMAMXMMMAMXMXXSAMMXMMMMMAXASMMMAXAMXAMXXMAXSMSASMMMMMAAXAMXAAASAMSSSMSSMMASAMAMSMSASXSASXSMSXMXMMMMMSMASAMAXAX
                    AMAAMXMXAXMXMMMXMXMASAXAAMASMMSMAMMMASMSAMAMMMMMSSMSMASAMXAAMMSSMMMXAMXSMMXSXSAAAAMMMMMAMMXSMMMAAAMAAMSMMMSXMAXMASAMXMASAMXAASASXXXMXMMMMSXM
                    SMXMXSSSXSAMXXMSMMSAMASMMSAMXAAMAXXSXMMSAMASASAAXAAAAAXSXMSMSMAAXSSMXSAXAMASAMMSSSSXXAXSMSMXXSMMSMMSSXAXMAMXXXXMAMXMAXXMAMMXMSAXMMSSXXMSASAM
                    MAMXMMAAAMAXXAAXAAMASAMMMMXSMSSMXMAXASXSXMXSXSMSSMMMMMSMMMMMAMMSMAAMXSXXAMXXAAAMAAAMMMSMAAASAXSAXMXAXMMSMASMMSMMMSAMMSSMMMSXMMMMAXAXXSAMAMSM
                    XAAXAMSMMMAMSMAMMMSAMMMXAAXXXAAASMSMXXAXXMXMAMXMAMSXASXAASASASMMAMMMASASMMSSMMXMMMMXAXXMSMSMAMMMMMMMSMXXXXXXAAMXASMSAAMAMXMASAMSSSMMAAMMMMMX
                    SXSXSXXXAXMXXXAXAMMMSMMSMSMMMXAXMAMAMMMMXMAMAMAMMAMAXAMSMXASASMXAMXMASMSAAXXXMMXXSMSMXSMXMXXSXMMASAXAAXMXMXMSMXMXSASMXXAMAMAMAMAAAXAXMXMXAXA
                    XSAMXMMSMSMMMSXSXMASAMAAAAAAAMMSMXMASAAXASASXSSSMSASMMAAMSMMXMASAMXMASXSMMMMAMMSMSAAAMSASXAXMAMSASXSASMMMSAMASAAAMAMAMSSXSSSXSMMSMMSXXAMMXSM
                    SAMXMAAAAAXAXXAMXSMSASXMSSMMXSAAXMSASMXXAMXSXAAXAMAMXXSMXAXSXMMASAAXAMXXXMASAAAAAMSMXMMAMMSXSAMMMSXMAMAXASAMAXMSMMMMXMAMMXAMAAMXAXXXAMAXMMAM
                    AXMAMMSMSMSMMSSMAXXSMMAAAXAAAMMSMMMXSASAMXMMMSMMAMSSXAMXSMAMASXAXSMSSSMMSSMSXSXMSMXXSMMAMAXMMMMSAMAMXMMMMSMMMSAAMAAXXMMSSMAMMMMSXSASMSSMAMAA
                    MMSXSAAXAXAMAAAMASMMASMMMSMMMSAMAXSAMAMAXSXAAAXMXMMAMXSAMXMMAMMMMMMMMAASASMMMMMMMMMMSAMMMMXAAMAMASMMAMASXMXAXAXMSSSSXSAAAMXMXXAXAMAXXAAXXMAX
                    SAXAMASMXMXMMSAMAMAAAAXMASMAMMMSAMMASMMMMASMSSMMASMXMAMMMXSMXSAAAXAXXMMMASXMASAAAAAXMAMXAAMSMMAMXMASMSASAMMXSAMXAXXAAAMSMMSMMASMXMAMMMSMMSSM
                    XSMMSSMAXMMAMXXSXMSMMMMMSSXXXAXMXXSMMXAMXMXMAAASASXSMXSAXXSAASXSXSSSMXMMMMXSASMSSSSSMXMSMMAXMMSXXSMMAMASXMAMSXSMMSAMMMXMXAAASMMMAXSXAAXAMAAM
                    MXMAMAXMASAASXMXAAMMMSMXMMXMSMSXSXAMAXMSAXMSSSMMASAMMAAXAMMMMSAAAMMMXAAAAMMMMSAMXMAXMXMASMSMMAXSMMXMXMXMXMXMMMMAXMAMAXAXMMSAMXMSSSMMMXSAMSSM
                    SAMMSMMXAMMXSAAMMMAAMAAXASAAAAMAMSMMAASXMAXAAXXMXMAMMAMMXAASMMMMXMAAXMSMSMAAAMMMAMASXXMAXXMAMASAXMASASAMXXXASAMXMXSMMSMMAMXAMXMAXAXMSASAMXAX
                    SXSXMXMMSMMASXSXSSSSSMSSMXASMSMAMAMMMXSAMXMMSMXMASAMXSSMMSXSMAAXASMSSXAAMMSMSMXSAMXMASAXSASXMXSMMXMSMSASAMSMSASXMAXAXMXSSMSAMSMMSXMMMASMMSSM
                    SMMAMASMAAMASMMXMAXAAMAMMMMXMAXAMAXXMAMASXXXAXASXMMMXMAXAMMMMSXSMSAMXXMMSAXMXAAXXXXMAMMXMMMASMXMAAXMASMMAMAASAMAMAMMXMASAAMSAAXXSMMAMXMAMAMA
                    SAXMSAMXSMMSSXSMMMMMXMAXXAMXMMSMMMSSMMSAMMMMMMMMAAAMASMMXSASMMMSMMMSAMXAMAXAMMSXSSSMMMSAMXSAMXAXSXSXAXASMMMMMXSXMMMSAMXSAMXMSSSXMAXSSXSAMASX
                    XMMXMAXXMXSASXSAMXXSXMASXSMMXAAXAXXMAAMXSASAAASMSMMXMMAAXMMXAXAMXAMXMXMSSSMSXAMXXAAXAAMMSAMASMMMXMMMMSMMAASXSAMMMAASXMXMASXMAXMAXSMAAASASXSM
                    MSMMSSXAAXMASMSAMAMAMMASMMMSMSSSMSAMSMMXSASMSMSAMAAASXSMMMASMMMSSMSASXXMAXAXMASXMSMMMSSMMAMAMASAXAAAXSXMSMMAMXSAMMMSASAMASASXMASAAMXMMMMXMMA
                    XAAAAXSMMMMXMASMMAXMSMASMXAAAAAAAMXMAASAMMXAAAMAMMSMSAMAAXMSAXSAAXMAMSAMXMSMMSAXAAXXAMAXSSMMSAMXSSSSSXSAAAXSMASXSAASXMAMASAMSAAMMXXSAMXAAXSS
                    MSMMSXXXAXXSAMXXSASXMXSMXMSMSMMMMMASXSMXSMMSMSMMMAMXMXMSSSXSXMMMSMMMMXXXSAMXXMXMSMSMMSXMAMMXMASAXMMXMASXSSMMMXSXSMXSMSXMXMMMSMXSXMSMSXMSAMAM
                    """;

    static int xmasCount = 0;

    public static void main(String[] args) {
        System.out.println("Hello world!");
/*        input = input.trim().replace("\r", "")
                .replace("\n", "")
                .replace("\t", "")
        ;*/
        var inputArray = input.split("\n");
        char[][] letters = new char[inputArray.length][inputArray[0].length()];
        for (int i = 0; i < inputArray.length; i++) {
            var line = inputArray[i];
            for (int j = 0; j < line.length(); j++) {
                letters[i][j] = line.charAt(j);
                //System.out.print(letters[i][j]);
            }
        }
        //System.out.println();
        searchXmas(letters);

        System.out.println(xmasCount);

    }

    private static void searchXmas(char[][] letters) {
        for (int i = 0; i < letters.length; i++) {
            var line = letters[i];
            for (int j = 0; j < line.length; j++) {
                var startSymbol = letters[i][j];
                if (startSymbol == 'A') {
                    List<UsedCell> usedCells = new ArrayList<>();
                    usedCells.add(new UsedCell(i, j));
                    makeDeepSearch(usedCells, letters);
                }
            }
        }
    }

    private static void makeDeepSearch(List<UsedCell> usedCells, char[][] letters) {
        if (usedCells.size() > 5) {
            return;
        }
        var lastUsedCell = usedCells.get(usedCells.size() - 1);
        //check upleft
        if (lastUsedCell.line == 0) {
            return;
        }
        if (lastUsedCell.line == letters.length - 1) {
            return;
        }
        if (lastUsedCell.column == 0) {
            return;
        }
        if (lastUsedCell.column == letters[0].length - 1) {
            return;
        }
        boolean a1=letters[lastUsedCell.line-1][lastUsedCell.column-1]=='S';
        boolean b1=letters[lastUsedCell.line-1][lastUsedCell.column-1]=='M';
        boolean a3=letters[lastUsedCell.line+1][lastUsedCell.column+1]=='S';
        boolean b3=letters[lastUsedCell.line+1][lastUsedCell.column+1]=='M';
        boolean correctDiag1 = a1 & b3 | b1 & a3;
        if (!correctDiag1) {
            return;
        }

        boolean a2=letters[lastUsedCell.line-1][lastUsedCell.column+1]=='S';
        boolean b2=letters[lastUsedCell.line-1][lastUsedCell.column+1]=='M';
        boolean a4=letters[lastUsedCell.line+1][lastUsedCell.column-1]=='S';
        boolean b4=letters[lastUsedCell.line+1][lastUsedCell.column-1]=='M';
        boolean correctDiag2 = a2 & b4 | b2 & a4;
        if (!correctDiag2) {
            return;
        }
        xmasCount++;
    }


    record UsedCell(int line, int column) {
    }
}


//51267 not correct
//2543  correct
//1930 second part